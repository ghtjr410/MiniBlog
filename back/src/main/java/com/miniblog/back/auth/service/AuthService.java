package com.miniblog.back.auth.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniblog.back.auth.dto.internal.TokensDTO;
import com.miniblog.back.auth.dto.request.LoginRequestDTO;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.repository.MemberRepository;
import com.miniblog.back.auth.response.LoginResponseWriter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final ObjectMapper objectMapper;
    private final TokenService tokenService;
    private final LoginResponseWriter loginResponseWriter;
    private final MemberRepository memberRepository;


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

    public void onLoginSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authResult) throws IOException {
        String username = authResult.getName();
        String deviceInfo = (String) httpServletRequest.getAttribute("deviceInfo");
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("유저를 찾지 못했습니다."));
        List<String> roles = TokenUtils.extractRolesFromAuthorities(authResult.getAuthorities());

        TokensDTO tokens = tokenService.generateTokens(member, roles, deviceInfo);
        loginResponseWriter.addCookie(httpServletResponse, tokens.refreshToken());

        loginResponseWriter.writeLoginSuccessResponse(httpServletResponse, tokens.accessToken());
    }

    public void onLoginFailure(HttpServletResponse httpServletResponse) throws IOException  {
        loginResponseWriter.writeLoginFailedResponse(httpServletResponse);
    }

    public void logout(String authorizationHeader) {
        String refreshToken = TokenUtils.extractToken(authorizationHeader);

        tokenService.revokeRefreshToken(refreshToken);
    }
}
