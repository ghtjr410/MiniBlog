package com.miniblog.api.auth.application.util;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS_TOKEN("access_token"),
    REFRESH_TOKEN("refresh_token");

    private final String key;

    TokenType(String key) {
        this.key = key;
    }
}
