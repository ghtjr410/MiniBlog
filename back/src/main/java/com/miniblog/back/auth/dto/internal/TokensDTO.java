package com.miniblog.back.auth.dto.internal;

public record TokensDTO(
        String accessToken,
        String refreshToken
) {
}
