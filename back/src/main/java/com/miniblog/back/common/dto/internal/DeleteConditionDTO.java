package com.miniblog.back.common.dto.internal;

public record DeleteConditionDTO(
        String column,
        Long value
) {
    public static DeleteConditionDTO byId(Long id) {
        return new DeleteConditionDTO("id", id);
    }

    public static DeleteConditionDTO byPostId(Long postId) {
        return new DeleteConditionDTO("post_id", postId);
    }

    public static DeleteConditionDTO byMemberId(Long memberId) {
        return new DeleteConditionDTO("member_id", memberId);
    }
}
