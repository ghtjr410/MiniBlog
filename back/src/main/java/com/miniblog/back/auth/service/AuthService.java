package com.miniblog.back.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniblog.back.auth.dto.internal.TokensDTO;
import com.miniblog.back.auth.dto.request.LoginRequestDTO;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.auth.util.TokenValidator;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.repository.MemberRepository;
import com.miniblog.back.auth.response.LoginResponseWriter;
import com.miniblog.back.auth.security.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final TokenValidator tokenValidator;
    private final LoginResponseWriter loginResponseWriter;

    public UsernamePasswordAuthenticationToken login(HttpServletRequest httpServletRequest) {
        try {
            LoginRequestDTO request = objectMapper.readValue(httpServletRequest.getInputStream(), LoginRequestDTO.class);

            if (request.deviceInfo() == null || request.deviceInfo().isBlank()) {
                throw new RuntimeException("Device information is required.");
            }

            httpServletRequest.setAttribute("deviceInfo", request.deviceInfo());

            // 인증 객체 생성
            return new UsernamePasswordAuthenticationToken(request.username(), request.password());

        } catch (IOException ex) {
            throw new RuntimeException("Invalid login request", ex);
        }
    }

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 로그인 정보 확인
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new PrincipalDetails(member);
    }

    public void onLoginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authResult) throws IOException {
        String username = authResult.getName();
        String deviceInfo = (String) httpServletRequest.getAttribute("deviceInfo");

        // Token 생성
        TokensDTO tokens = tokenService.generateTokens(username, authResult.getAuthorities(), deviceInfo);
        log.info(tokens.toString());
        // Refresh Token 쿠키 저장
        loginResponseWriter.addCookie(httpServletResponse, tokens.refreshToken());

        // JSON 응답 작성
        loginResponseWriter.writeJsonResponse(httpServletResponse, tokens.accessToken());
    }

    public void onLoginFail() {

    }

    public void logout(String authorizationHeader) {
        String refreshToken = TokenUtils.extractToken(authorizationHeader);

        tokenService.revokeRefreshToken(refreshToken);
        log.info("Refresh Token {} has been revoked.", refreshToken);
    }

    public boolean validateToken(String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);
        if (token.isBlank()) {
            return false;
        }

        if (!tokenValidator.validateToken(token)) {
            return false;
        }

        if (tokenService.isTokenBlacklisted(token)) {
            return false;
        }

        return true;
    }

    public Authentication getAuthenticationFromToken(String authorizationHeader) {
        String token = TokenUtils.extractToken(authorizationHeader);

        boolean isAccessToken = tokenValidator.isAccessToken(token);
        String username = tokenValidator.getUsernameFromToken(token); // 토큰에서 사용자 정보 추출

        List<GrantedAuthority> authorities = isAccessToken
                ? tokenValidator.getAuthoritiesFromToken(token) // Access Token의 권한 정보
                : List.of(); // Refresh Token의 경우 권한 없음

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

}
