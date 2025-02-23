package com.miniblog.api.auth.application.dto;

import com.miniblog.api.auth.domain.RefreshToken;

import java.time.LocalDateTime;

public record TokenValidateResult(
        String refreshToken,
        LocalDateTime expiresDate,
        long memberId
) {
    public static TokenValidateResult of(RefreshToken validatedToken) {
        return new TokenValidateResult(
                validatedToken.getToken(),
                validatedToken.getExpiresDate(),
                validatedToken.getId()
        );
    }
}
