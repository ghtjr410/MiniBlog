package com.miniblog.back.comment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequestDTO(
        @NotNull
        Long postId,

        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
