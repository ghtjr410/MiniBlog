package com.miniblog.api.comment.application.dto;

import com.miniblog.api.comment.api.dto.request.CommentEditRequest;

public record CommentEditData(
        String content,
        long commentId,
        long memberId
) {
    public static CommentEditData of(CommentEditRequest request, long commentId, long memberId) {
        return new CommentEditData(request.content(), commentId, memberId);
    }
}
