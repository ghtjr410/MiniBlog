package com.miniblog.back.auth.service;

import com.miniblog.back.auth.dto.request.RefreshRequestDTO;
import com.miniblog.back.auth.dto.response.AccessTokenResponseDTO;
import com.miniblog.back.auth.model.Token;
import com.miniblog.back.auth.mapper.TokenMapper;
import com.miniblog.back.auth.response.LoginResponseWriter;
import com.miniblog.back.auth.token.TokenParser;
import com.miniblog.back.auth.token.TokenProvider;
import com.miniblog.back.auth.token.TokenValidator;
import com.miniblog.back.auth.util.*;
import com.miniblog.back.auth.repository.TokenRepository;
import com.miniblog.back.auth.dto.internal.TokensDTO;
import com.miniblog.back.auth.dto.internal.RefreshTokenInfoDTO;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.repository.MemberRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final BlacklistTokenService blacklistTokenService;
    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;
    private final TokenParser tokenParser;
    private final TokenMapper tokenMapper;
    private final LoginResponseWriter loginResponseWriter;

    @Transactional
    public TokensDTO generateTokens(Member member, List<String> roles, String deviceInfo) {
        UserInfoDTO userInfoDTO = UserInfoDTO.of(member);

        String accessToken = tokenProvider.createAccessToken(userInfoDTO, roles);
        RefreshTokenInfoDTO refreshTokenResult = tokenProvider.createRefreshToken(userInfoDTO);

        Token token = tokenMapper.create(member, refreshTokenResult.refreshToken(), refreshTokenResult.expiresDate(), deviceInfo);
        tokenRepository.save(token);
        log.info("리프레시토큰 ! : {}", refreshTokenResult.refreshToken());
        return TokensDTO.of(accessToken, refreshTokenResult.refreshToken());
    }

    @Transactional
    public void revokeRefreshToken(String refreshToken) {

        tokenValidator.validateToken(refreshToken);

        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException("토큰을 찾지 못했습니다."));
        tokenRepository.delete(token);

        blacklistTokenService.addToBlacklist(refreshToken);
    }

    public void validateToken(String authorizationHeader) {

        String token = TokenUtils.extractToken(authorizationHeader);
        log.info("3. 토큰 값 : {}", token);
        if (token.isBlank()) {
            throw new SecurityException("토큰이 존재하지 않습니다.");
        }

        tokenValidator.validateToken(token);
        log.info("4. 토큰 검증 성공");

        if(blacklistTokenService.isBlacklistedWithLua(token)) {
            log.info("6. 블랙리스트 포함");
            throw new SecurityException("블랙리스트에 등록된 토큰입니다.");
        }
        log.info("6. 블랙리스트 미포함");
    }

    public Authentication getAuthenticationFromToken(String authorizationHeader) {

        String token = TokenUtils.extractToken(authorizationHeader);

        boolean isAccessToken = tokenParser.isAccessToken(token);
        String username = tokenParser.getUsernameFromToken(token);
        log.info("7. 엑세스 토큰? : {}, 유저네임 : {}", isAccessToken, username);

        List<GrantedAuthority> authorities = isAccessToken
                ? tokenParser.getAuthoritiesFromToken(token)
                : List.of();
        log.info("8. 롤 감지 : {}", authorities);
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    @Transactional
    public AccessTokenResponseDTO refresh(String authorizationHeader, RefreshRequestDTO requestDTO, HttpServletResponse httpServletResponse) {

        String refreshToken = TokenUtils.extractToken(authorizationHeader);
        String username = tokenParser.getUsernameFromToken(refreshToken);

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("유저를 찾지 못했습니다."));
        List<String> roles = List.of(member.getRole().toString());

        TokensDTO tokensDTO = generateTokens(member, roles, requestDTO.deviceInfo());
        loginResponseWriter.addCookie(httpServletResponse, tokensDTO.refreshToken());
        revokeRefreshToken(refreshToken);

        return AccessTokenResponseDTO.of(tokensDTO.accessToken());
    }
}
