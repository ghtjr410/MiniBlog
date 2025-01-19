package com.miniblog.back.post.dto.response;

import com.miniblog.back.post.model.Post;

import java.time.LocalDateTime;

public record PostUpdatedResponseDTO(
        String title,
        String content,
        LocalDateTime updatedDate
) {
    public static PostUpdatedResponseDTO of(Post post) {
        return new PostUpdatedResponseDTO(
                post.getTitle(),
                post.getContent(),
                post.getUpdatedDate()
        );
    }
}
