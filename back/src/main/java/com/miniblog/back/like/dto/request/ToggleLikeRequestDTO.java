package com.miniblog.back.like.dto.request;

import jakarta.validation.constraints.NotNull;

public record ToggleLikeRequestDTO(
        @NotNull
        Long postId
) {
}
