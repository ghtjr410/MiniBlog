package com.miniblog.api.auth.infrastructure.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniblog.api.auth.application.dto.MemberInfoData;
import com.miniblog.api.auth.application.dto.RefreshTokenClaimsData;
import com.miniblog.api.auth.application.port.TokenParser;
import com.miniblog.api.auth.application.util.JwtClaims;
import com.miniblog.api.auth.application.util.TokenType;
import com.miniblog.api.common.domain.exception.ResourceExpiredException;
import com.miniblog.api.common.domain.exception.ResourceInvalidException;
import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.common.infrastructure.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_INVALID_CREDENTIALS;
import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_TOKEN_EXPIRED;

@Component
@RequiredArgsConstructor
public class TokenParserImpl implements TokenParser {

    private final JwtProperties jwtProperties;
    private final ObjectMapper objectMapper;

    @Override
    public MemberInfoData extractMemberInfo(String token) {
        Claims claims = parseClaims(token);
        validateTokenExpiration(claims);

        return MemberInfoData.of(
                extractSubjectAsLong(claims),
                extractUsername(claims),
                extractNickname(claims),
                extractEmail(claims),
                extractRolesIfAccessToken(claims));
    }

    @Override
    public RefreshTokenClaimsData extractRefreshTokenClaims(String token) {
        Claims claims = parseClaims(token);
        validateTokenType(claims, TokenType.REFRESH_TOKEN);

        return RefreshTokenClaimsData.of(
                extractSubjectAsLong(claims),
                extractExpiration(claims));
    }

    private LocalDateTime extractExpiration(Claims claims) {
        Date expiration = claims.getExpiration();

        if (expiration.before(new Date())) {
            throw new ResourceExpiredException(AUTH_TOKEN_EXPIRED);
        }

        return convertToLocalDateTime(expiration);
    }

    private String extractSubject(Claims claims) {
        return claims.getSubject();
    }

    private String extractUsername(Claims claims) {
        return claims.get(JwtClaims.USERNAME.getKey(), String.class);
    }

    private String extractNickname(Claims claims) {
        return claims.get(JwtClaims.NICKNAME.getKey(), String.class);
    }

    private String extractEmail(Claims claims) {
        return claims.get(JwtClaims.EMAIL.getKey(), String.class);
    }

    private List<String> extractRoles(Claims claims) {
        return objectMapper.convertValue(
                claims.get(JwtClaims.ROLES.getKey()), new TypeReference<List<String>>() {});
    }

    private LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    private Long extractSubjectAsLong(Claims claims) {
        return Long.valueOf(extractSubject(claims).trim());
    }

    private List<String> extractRolesIfAccessToken(Claims claims) {
        return isAccessToken(claims) ? extractRoles(claims) : List.of();
    }

    private boolean isAccessToken(Claims claims) {
        String type = claims.get(JwtClaims.TOKEN_TYPE.getKey(), String.class);
        return StringUtils.equals(TokenType.ACCESS_TOKEN.getKey(), type);
    }

    private void validateTokenType(Claims claims, TokenType tokenType) {
        String type = claims.get(JwtClaims.TOKEN_TYPE.getKey(), String.class);
        if (!StringUtils.equals(tokenType.getKey(), type)) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS);
        }
    }

    private void validateTokenExpiration(Claims claims) {
        LocalDateTime expiration = claims.getExpiration().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();

        if (expiration.isBefore(LocalDateTime.now())) {
            throw new ResourceExpiredException(AUTH_TOKEN_EXPIRED);
        }
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new ResourceExpiredException(AUTH_TOKEN_EXPIRED); // 커스텀 예외로 변환
        } catch (InvalidClaimException e) {
            throw new ResourceInvalidException(AUTH_INVALID_CREDENTIALS); // 커스텀 예외로 변환
        } catch (SignatureException e) {
            throw new ResourceUnauthorizedException(AUTH_INVALID_CREDENTIALS); // 추가 처리
        }
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
