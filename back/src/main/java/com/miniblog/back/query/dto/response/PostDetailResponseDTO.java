package com.miniblog.back.query.dto.response;

import com.miniblog.back.query.dto.internal.CommentDTO;
import com.miniblog.back.query.dto.internal.LikeHistoryDTO;
import com.miniblog.back.query.dto.internal.PostDetailDTO;

import java.util.List;

public record PostDetailResponseDTO(
        PostDetailDTO post,
        List<CommentDTO> comments,
        List<LikeHistoryDTO> likeHistories
) {
    public static PostDetailResponseDTO of(
            PostDetailDTO post,
            List<CommentDTO> comments,
            List<LikeHistoryDTO> likeHistories
    ) {
        return new PostDetailResponseDTO(
                post,
                comments,
                likeHistories
        );
    }
}
