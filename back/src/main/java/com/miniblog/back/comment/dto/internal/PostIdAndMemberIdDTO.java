package com.miniblog.back.comment.dto.internal;

public record PostIdAndMemberIdDTO(
        Long postId,
        Long memberId
) {
}
