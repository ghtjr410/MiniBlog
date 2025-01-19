package com.miniblog.back.auth.util;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS_TOKEN("ACCESS_TOKEN"),
    REFRESH_TOKEN("REFRESH_TOKEN");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

}