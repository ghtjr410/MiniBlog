package com.miniblog.back.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequestDTO(
        @NotBlank
        String deviceInfo
) {
}
