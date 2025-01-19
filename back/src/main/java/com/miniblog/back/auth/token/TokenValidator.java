package com.miniblog.back.auth.token;

import com.miniblog.back.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final TokenParser tokenParser;
    private final JwtProperties jwtProperties;

    public void validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(jwtProperties.getSecretKey())
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            // 여기서 예외 발생 시, Invalid token 처리
            throw new SecurityException("Invalid token", e);
        }
    }

    public LocalDateTime getExpirationDate(String token) {
        Claims claims = tokenParser.parseClaims(token);
        return claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
}
