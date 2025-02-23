package com.miniblog.api.auth.application.dto;

import com.miniblog.api.auth.api.dto.request.LoginRequest;

public record LoginData(
        String username,
        String password,
        String deviceInfo
) {
    public static LoginData of(LoginRequest request) {
        return new LoginData(request.username(), request.password(), request.deviceInfo());
    }
}
