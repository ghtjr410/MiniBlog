package com.miniblog.back.auth.dto.internal;

public record RefreshTokenDTO(
        String refreshToken
) {
    public static RefreshTokenDTO of(String refreshToken) {
        return new RefreshTokenDTO(
                refreshToken
        );
    }
}
