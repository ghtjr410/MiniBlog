package com.miniblog.api.auth.application.dto;

import java.time.LocalDateTime;

public record AccessTokenInfoData(
        String accessToken,
        String tokenType,
        LocalDateTime expiresDate
) {
    public static AccessTokenInfoData of(String accessToken, String tokenType, LocalDateTime expiresDate) {
        return new AccessTokenInfoData(accessToken, tokenType, expiresDate);
    }
}
