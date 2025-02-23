package com.miniblog.api.auth.application.dto;

import java.time.LocalDateTime;

public record RefreshTokenInfoData(
        String refreshToken,
        LocalDateTime expiresDate
) {
    public static RefreshTokenInfoData of(String refreshToken, LocalDateTime expiresDate) {
        return new RefreshTokenInfoData(refreshToken, expiresDate);
    }
}
