package com.miniblog.back.auth.token;

import com.miniblog.back.auth.config.JwtProperties;
import com.miniblog.back.auth.util.TokenType;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenParser {
    private static final Logger log = LoggerFactory.getLogger(TokenParser.class);
    private final JwtProperties jwtProperties;

    public Claims parseClaims(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(jwtProperties.getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public UserInfoDTO getUserInfo(String token) {
        Claims claims = parseClaims(token);

        return UserInfoDTO.of(
                Long.valueOf(claims.getSubject().trim()),
                claims.get("username", String.class),
                getUsernameFromToken(token),
                claims.get("email", String.class)
        );
    }

    public boolean isAccessToken(String token) {
        Claims claims = parseClaims(token);
        String type = claims.get("type", String.class);
        log.info("type 은? : {}", type);
        log.info("TokenType.ACCESS_TOKEN.getValue() : {} == type: {}", TokenType.ACCESS_TOKEN.getValue(), type);

        return TokenType.ACCESS_TOKEN.getValue().equals(type);
    }

    public List<GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = parseClaims(token);
        List<String> roles = claims.get("roles", List.class);

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getUsernameFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("username", String.class);
    }
}
