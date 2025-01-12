package com.miniblog.back.auth.util;

import com.miniblog.back.auth.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenValidator {

    private final JwtProperties jwtProperties;

    private Claims parseClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

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
        Claims claims = parseClaims(token);
        return claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.getSubject();
    }

    public boolean isAccessToken(String token) {
        Claims claims = parseClaims(token);
        String type = claims.get("type", String.class);

        return TokenType.ACCESS_TOKEN.getValue().equals(type);
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = parseClaims(token);
        List<String> roles = claims.get("roles", List.class);

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
