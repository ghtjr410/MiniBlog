package com.miniblog.back.like.dto.response;

public record ToggleLikeResponseDTO(
        boolean likeResult
) {
    public static ToggleLikeResponseDTO of(boolean likeResult) {
        return new ToggleLikeResponseDTO(
                likeResult
        );
    }
}
