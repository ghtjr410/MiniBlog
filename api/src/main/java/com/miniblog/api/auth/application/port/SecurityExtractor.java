package com.miniblog.api.auth.application.port;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public interface SecurityExtractor {
    public String extractToken(String authHeader);
    public List<String> extractRoles(Collection<? extends GrantedAuthority> authorities);
}
