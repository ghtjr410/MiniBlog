package com.miniblog.back.comment.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequestDTO(
        @NotBlank(message = "내용은 필수입니다.")
        String content
) {
}
