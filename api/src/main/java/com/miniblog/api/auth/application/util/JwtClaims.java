package com.miniblog.api.auth.application.util;

import lombok.Getter;

@Getter
public enum JwtClaims {
    USERNAME("username"),
    NICKNAME("nickname"),
    EMAIL("email"),
    ROLES("roles"),
    TOKEN_TYPE("type");

    private final String key;

    JwtClaims(String key) {
        this.key = key;
    }
}
