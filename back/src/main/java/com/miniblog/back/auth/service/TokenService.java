package com.miniblog.back.auth.service;

import com.miniblog.back.auth.dto.internal.AccessTokenClaimsDTO;
import com.miniblog.back.auth.dto.request.RefreshRequestDTO;
import com.miniblog.back.auth.dto.response.RefreshResponseDTO;
import com.miniblog.back.auth.model.Token;
import com.miniblog.back.auth.mapper.TokenMapper;
import com.miniblog.back.auth.response.LoginResponseWriter;
import com.miniblog.back.auth.util.TokenProvider;
import com.miniblog.back.auth.repository.TokenRepository;
import com.miniblog.back.auth.dto.internal.TokensDTO;
import com.miniblog.back.auth.dto.internal.RefreshTokenInfoDTO;
import com.miniblog.back.auth.util.TokenType;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.auth.util.TokenValidator;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    private final TokenProvider tokenProvider;
    private final TokenValidator tokenValidator;
    private final TokenMapper tokenMapper;
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final LoginResponseWriter loginResponseWriter;
    private final BlacklistTokenService blacklistTokenService;

    @Transactional
    public TokensDTO generateTokens(Member member, List<String> roles, String deviceInfo) {

        AccessTokenClaimsDTO tokenClaims = new AccessTokenClaimsDTO(roles, TokenType.ACCESS_TOKEN.getValue());

        String accessToken = tokenProvider.createAccessToken(member.getUsername(), tokenClaims);
        RefreshTokenInfoDTO refreshTokenResult = tokenProvider.createRefreshToken(member.getUsername());

        Token token = tokenMapper.create(member, refreshTokenResult.refreshToken(), refreshTokenResult.expiresDate(), deviceInfo);
        tokenRepository.save(token);

        return new TokensDTO(accessToken, refreshTokenResult.refreshToken());
    }

    @Transactional
    public void revokeRefreshToken(String refreshToken) {

        tokenValidator.validateToken(refreshToken);

        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Token not found in database."));
        tokenRepository.delete(token);

        LocalDateTime expiresDate = tokenValidator.getExpirationDate(refreshToken);
        blacklistTokenService.addToBlacklist(refreshToken, expiresDate);

        log.info("Refresh Token {} has been revoked and added to blacklist.", refreshToken);
    }

    public void validateToken(String authorizationHeader) {

        String token = TokenUtils.extractToken(authorizationHeader);
        if (token.isBlank()) {
            throw new SecurityException("Empty token");
        }

        tokenValidator.validateToken(token);

        if(blacklistTokenService.isBlacklistedWithLua(token)) {
            throw new SecurityException("Blacklisted token");
        }
    }

    public Authentication getAuthenticationFromToken(String authorizationHeader) {

        String token = TokenUtils.extractToken(authorizationHeader);

        boolean isAccessToken = tokenValidator.isAccessToken(token);
        String username = tokenValidator.getUsernameFromToken(token);

        List<GrantedAuthority> authorities = isAccessToken
                ? tokenValidator.getAuthoritiesFromToken(token)
                : List.of();

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    @Transactional
    public RefreshResponseDTO refresh(String authorizationHeader, RefreshRequestDTO requestDTO, HttpServletResponse httpServletResponse) {

        String refreshToken = TokenUtils.extractToken(authorizationHeader);
        String username = tokenValidator.getUsernameFromToken(refreshToken);

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        List<String> roles = List.of(member.getRole().toString());

        TokensDTO tokensDTO = generateTokens(member, roles, requestDTO.deviceInfo());
        loginResponseWriter.addCookie(httpServletResponse, tokensDTO.refreshToken());
        revokeRefreshToken(refreshToken);

        return new RefreshResponseDTO(tokensDTO.accessToken());
    }
}
