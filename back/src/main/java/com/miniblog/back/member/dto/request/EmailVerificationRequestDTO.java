package com.miniblog.back.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record EmailVerificationRequestDTO(
        @NotBlank
        @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문자와 숫자만 포함할 수 있습니다.")
        String username,

        @NotBlank
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email
) {
}
