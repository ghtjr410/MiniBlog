package com.miniblog.back.auth.util;

import com.miniblog.back.auth.config.JwtProperties;
import com.miniblog.back.auth.dto.internal.RefreshTokenInfoDTO;
import com.miniblog.back.auth.dto.internal.AccessTokenClaimsDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    // Access Token 생성
    public String createAccessToken(String username, AccessTokenClaimsDTO tokenClaims) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", tokenClaims.roles())
                .claim("type", tokenClaims.type())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // Refresh Token 생성
    public RefreshTokenInfoDTO createRefreshToken(String username) {
        long expirationTimeMillis = System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration();
        LocalDateTime expiresDate = LocalDateTime.ofEpochSecond(expirationTimeMillis / 1000, 0, java.time.ZoneOffset.UTC);

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .claim("type", TokenType.REFRESH_TOKEN.getValue())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        return new RefreshTokenInfoDTO(refreshToken, expiresDate);
    }
}
