package com.miniblog.api.member.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsernameRecoverVerifyRequest(
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        String email,

        @NotBlank(message = "인증코드는 필수 입력 항목입니다.")
        @Size(min = 10, max = 10, message = "인증코드는 10자리여야 합니다.")
        String code
) {
}
