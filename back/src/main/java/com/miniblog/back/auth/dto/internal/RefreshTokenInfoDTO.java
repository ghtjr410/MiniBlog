package com.miniblog.back.auth.dto.internal;

import java.time.LocalDateTime;

public record RefreshTokenInfoDTO(
        String refreshToken,
        LocalDateTime expiresDate
) {
}
