package com.miniblog.api.auth.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record TokenRenewalRequest(
        @NotBlank
        String deviceInfo
) {
}
