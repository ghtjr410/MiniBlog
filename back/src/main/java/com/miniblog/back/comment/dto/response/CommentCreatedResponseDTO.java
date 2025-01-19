package com.miniblog.back.comment.dto.response;

import com.miniblog.back.comment.model.Comment;
import com.miniblog.back.common.dto.internal.UserInfoDTO;

import java.time.LocalDateTime;

public record CommentCreatedResponseDTO(
        Long id,
        String content,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
    public static CommentCreatedResponseDTO of(Comment comment) {
        return new CommentCreatedResponseDTO(
                comment.getId(),
                comment.getContent(),
                comment.getCreatedDate(),
                comment.getUpdatedDate()
        );
    }
}
