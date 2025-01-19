package com.miniblog.back.auth.dto.response;

public record AccessTokenResponseDTO(
        String accessToken
) {
    public static AccessTokenResponseDTO of(String accessToken) {
        return new AccessTokenResponseDTO(
                accessToken
        );
    }
}
