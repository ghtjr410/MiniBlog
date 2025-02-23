package com.miniblog.api.post.application.dto;

import com.miniblog.api.post.api.dto.request.PostEditRequest;

public record PostEditData(
        long postId,
        String title,
        String content,
        long memberId
) {
    public static PostEditData of(long postId, PostEditRequest request, long memberId) {
        return new PostEditData(postId, request.title(), request.content(), memberId);
    }
}
