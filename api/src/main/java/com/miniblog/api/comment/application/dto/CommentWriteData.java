package com.miniblog.api.comment.application.dto;

import com.miniblog.api.comment.api.dto.request.CommentWriteRequest;

public record CommentWriteData(
        String content,
        long postId,
        long memberId
) {
    public static CommentWriteData of(CommentWriteRequest request, long memberId) {
        return new CommentWriteData(request.content(), request.postId(), memberId);
    }
}
