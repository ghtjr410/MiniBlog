package com.miniblog.back.viewcount.dto.request;

import jakarta.validation.constraints.NotNull;

public record IncrementViewCountRequestDTO(
        @NotNull
        Long postId
) {
}
