package com.miniblog.api.auth.application.dto;

public record LogoutData(
        String refreshToken,
        String deviceInfo
) {
    public static LogoutData of(String refreshToken, String deviceInfo) {
        return new LogoutData(refreshToken, deviceInfo);
    }
}
