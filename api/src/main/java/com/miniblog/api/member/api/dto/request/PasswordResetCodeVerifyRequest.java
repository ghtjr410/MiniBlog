package com.miniblog.api.member.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PasswordResetCodeVerifyRequest(
        @NotBlank(message = "사용자명은 필수 입력 항목입니다.")
        String username,

        @NotBlank(message = "인증코드는 필수 입력 항목입니다.")
        @Size(min = 10, max = 10, message = "인증코드는 10자리여야 합니다.")
        String code
) {
}
