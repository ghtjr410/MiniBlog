package com.miniblog.api.auth.infrastructure.security;

import com.miniblog.api.auth.application.port.SecurityExtractor;
import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.miniblog.api.auth.domain.AuthErrorMessage.AUTH_MISSING_CREDENTIALS;

@Component
public class SecurityExtractorImpl implements SecurityExtractor {

    /**
     * 헤더에서 JWT 토큰 추출
     * 유효하지 않은 경우 예외 발생
     */
    @Override
    public String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "").trim();
        }
        throw new ResourceUnauthorizedException(AUTH_MISSING_CREDENTIALS);
    }

    /**
     * role 목록 추출
     */
    @Override
    public List<String> extractRoles(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }
}
