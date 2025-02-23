package com.miniblog.api.comment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentWriteRequest(
        @NotNull
        Long postId,

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        @Size(min = 1, max = 1000, message = "내용은 1000자 이하로 입력해야 합니다.")
        String content
) {
}
