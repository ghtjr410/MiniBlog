package com.miniblog.back.post.dto.response;

import com.miniblog.back.common.dto.internal.UserInfoDTO;
import com.miniblog.back.post.model.Post;

import java.time.LocalDateTime;

public record PostCreatedResponseDTO(
        Long id,
        String title,
        String content,
        LocalDateTime createdDate,
        LocalDateTime updatedDate
) {
    public static PostCreatedResponseDTO of(Post post) {
        return new PostCreatedResponseDTO(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate(),
                post.getUpdatedDate()
        );
    }
}
