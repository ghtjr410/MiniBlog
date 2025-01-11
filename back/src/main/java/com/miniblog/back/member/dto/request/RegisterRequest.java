package com.miniblog.back.member.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank
        @Size(min = 5, max = 20, message = "아이디는 5자 이상 20자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "아이디는 영문자와 숫자만 포함할 수 있습니다.")
        String username,

        @NotBlank
        @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
        message = "비밀번호는 최소 하나의 영문자, 하나의 숫자, 하나의 특수문자를 포함해야 합니다.")
        String password,

        @NotBlank
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        String email,

        @NotBlank
        @Size(min = 2, max = 6, message = "닉네임은 2자 이상 6자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$",
        message = "닉네임은 공백이나 특수문자를 포함할 수 없습니다.")
        String nickname
) {
}
