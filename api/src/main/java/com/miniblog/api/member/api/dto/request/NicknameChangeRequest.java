package com.miniblog.api.member.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NicknameChangeRequest(
        @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
        @Size(min = 2, max = 6, message = "닉네임은 2자 이상 6자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^(?!\\d+$)[a-zA-Z0-9가-힣]+$", message = "닉네임은 숫자로만 구성될 수 없습니다.")
        String nickname
) {
}
