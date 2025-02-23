package com.miniblog.api.post.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostWriteRequest(
        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        @Size(min = 1, max = 100, message = "제목은 5자 이상 100자 이하로 입력해야 합니다.")
        String title,

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        String content
) {
}
