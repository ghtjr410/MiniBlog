package com.miniblog.api.member.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DeleteAccountRequest(
        @NotBlank
        String deviceInfo
) {
}
