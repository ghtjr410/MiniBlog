package com.miniblog.back.comment.dto.response;

import com.miniblog.back.comment.model.Comment;

import java.time.LocalDateTime;

public record CommentUpdatedResponseDTO(
        String content,
        LocalDateTime updatedDate
) {
    public static CommentUpdatedResponseDTO of(Comment comment) {
        return new CommentUpdatedResponseDTO(
                comment.getContent(),
                comment.getUpdatedDate()
        );
    }
}
