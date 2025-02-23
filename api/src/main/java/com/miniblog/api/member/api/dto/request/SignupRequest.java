package com.miniblog.api.member.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(
        @NotBlank(message = "사용자명은 필수 입력 항목입니다.")
        @Size(min = 6, max = 20, message = "사용자명은 6자 이상 20자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z0-9]+$", message = "사용자명은 영문과 숫자를 포함해야 합니다.")
        String username,


        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[A-Za-z\\d!@#$%^&*(),.?\":{}|<>]+$",
                message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다.")
        String password,

        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        String email,

        @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
        @Size(min = 2, max = 6, message = "닉네임은 2자 이상 6자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^(?!\\d+$)[a-zA-Z0-9가-힣]+$", message = "닉네임은 숫자로만 구성될 수 없습니다.")
        String nickname
) {
    public static SignupRequest of(String username, String password, String email, String nickname) {
        return new SignupRequest(username, password, email, nickname);
    }
}
