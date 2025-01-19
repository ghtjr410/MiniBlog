package com.miniblog.back.member.dto.request;

import com.miniblog.back.common.util.ValidationUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record NicknameAvailableRequestDTO(
        @NotBlank
        @Size(min = 2, max = 6, message = "닉네임은 2자 이상 6자 이하로 입력해야 합니다.")
        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+$",
                message = "닉네임은 공백이나 특수문자를 포함할 수 없습니다.")
        String nickname
) {
        public static NicknameAvailableRequestDTO of(String nickname) {
                NicknameAvailableRequestDTO requestDTO = new NicknameAvailableRequestDTO(nickname);
                ValidationUtil.validateRequest(requestDTO);
                return requestDTO;
        }
}
