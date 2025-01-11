package com.miniblog.back.auth.dto.request;

public record LoginRequestDTO(
        String username,
        String password,
        String deviceInfo
) {
}
