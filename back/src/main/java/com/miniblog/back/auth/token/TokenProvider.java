package com.miniblog.back.auth.token;

import com.miniblog.back.auth.config.JwtProperties;
import com.miniblog.back.auth.dto.internal.RefreshTokenInfoDTO;
import com.miniblog.back.auth.util.TokenType;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    // Access Token 생성
    public String createAccessToken(UserInfoDTO userInfoDTO, List<String> roles) {
        return Jwts.builder()
                .setSubject(userInfoDTO.id().toString())
                .claim("username", userInfoDTO.username())
                .claim("nickname", userInfoDTO.nickname())
                .claim("email", userInfoDTO.email())
                .claim("roles", roles)
                .claim("type", TokenType.ACCESS_TOKEN)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // Refresh Token 생성
    public RefreshTokenInfoDTO createRefreshToken(UserInfoDTO userInfoDTO) {
        long expirationTimeMillis = System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration();
        LocalDateTime expiresDate = LocalDateTime.ofEpochSecond(expirationTimeMillis / 1000, 0, java.time.ZoneOffset.UTC);

        String refreshToken = Jwts.builder()
                .setSubject(userInfoDTO.id().toString())
                .claim("username", userInfoDTO.username())
                .claim("nickname", userInfoDTO.nickname())
                .claim("email", userInfoDTO.email())
                .claim("type", TokenType.REFRESH_TOKEN.getValue())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();

        return new RefreshTokenInfoDTO(refreshToken, expiresDate);
    }
}
