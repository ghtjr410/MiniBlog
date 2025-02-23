package com.miniblog.api.auth.application.dto;

import java.time.LocalDateTime;

public record RefreshTokenClaimsData(
        long memberId,
        LocalDateTime expiresDate
) {
    public static RefreshTokenClaimsData of(long memberId, LocalDateTime expiresDate) {
        return new RefreshTokenClaimsData(memberId, expiresDate);
    }
}
