package com.miniblog.back.auth.dto.internal;

public record TokensDTO(
        String accessToken,
        String refreshToken
) {
    public static TokensDTO of(String accessToken, String refreshToken) {
        return new TokensDTO(
                accessToken,
                refreshToken
        );
    }
}
