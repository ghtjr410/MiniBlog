package com.miniblog.api.auth.infrastructure.security;

import com.miniblog.api.auth.application.dto.AccessTokenInfoData;
import com.miniblog.api.auth.application.dto.JwtTokensData;
import com.miniblog.api.auth.application.dto.RefreshTokenInfoData;
import com.miniblog.api.auth.application.port.TokenProvider;
import com.miniblog.api.auth.application.util.JwtClaims;
import com.miniblog.api.auth.application.util.TokenType;
import com.miniblog.api.common.infrastructure.config.JwtProperties;
import com.miniblog.api.member.domain.Member;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProviderImpl implements TokenProvider {
    private final JwtProperties jwtProperties;

    @Override
    public JwtTokensData generateTokens(Member member, List<String> roles) {
        String accessToken = generateAccessToken(member, roles);
        LocalDateTime accessTokenExpiresDate = getAccessTokenExpiresDate();

        AccessTokenInfoData accessInfo = AccessTokenInfoData.of(accessToken, "Bearer", accessTokenExpiresDate);

        String refreshToken = generateRefreshToken(member);
        LocalDateTime refreshTokenExpiresDate = getRefreshTokenExpiresDate();

        RefreshTokenInfoData refreshInfo = RefreshTokenInfoData.of(refreshToken, refreshTokenExpiresDate);

        return JwtTokensData.of(accessInfo, refreshInfo);
    }

    @Override
    public String generateAccessToken(Member member, List<String> roles) {
        return buildToken(member, roles, TokenType.ACCESS_TOKEN, jwtProperties.getAccessTokenExpiration());
    }

    @Override
    public String generateRefreshToken(Member member) {
        return buildToken(member, null, TokenType.REFRESH_TOKEN, jwtProperties.getRefreshTokenExpiration());
    }

    private String buildToken(Member member, List<String> roles, TokenType tokenType, long expirationTime) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(member.getId().toString())
                .claim(JwtClaims.USERNAME.getKey(), member.getUsername())
                .claim(JwtClaims.NICKNAME.getKey(), member.getNickname())
                .claim(JwtClaims.EMAIL.getKey(), member.getEmail())
                .claim(JwtClaims.TOKEN_TYPE.getKey(), tokenType.getKey())
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setExpiration(Timestamp.valueOf(calculateExpirationDate(expirationTime)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256);

        if (roles != null) {
            jwtBuilder.claim(JwtClaims.ROLES.getKey(), roles);
        }

        return jwtBuilder.compact();
    }

    private LocalDateTime getAccessTokenExpiresDate() {
        return calculateExpirationDate(jwtProperties.getAccessTokenExpiration());
    }

    private LocalDateTime getRefreshTokenExpiresDate() {
        return calculateExpirationDate(jwtProperties.getRefreshTokenExpiration());
    }

    private LocalDateTime calculateExpirationDate(long expirationMillis) {
        return  LocalDateTime.now()
                .plus(Duration.ofMillis(expirationMillis))
                .withNano(0);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
