package com.housing.back.dto.response.post;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.housing.back.entity.PostEntity;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDetailResponseDto {
    private Long postId;
    private String nickname;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int viewCount;
    private int likeCount;
    private List<CommentDto> comments;

    @Getter
    @Builder
    public static class CommentDto {
        private Long commentId;
        private String nickname;
        private String content;
        private LocalDateTime createdAt;
        private boolean isOwner;
    }

    public static PostDetailResponseDto fromEntity(PostEntity postEntity, String currentUserNickname) {
        List<CommentDto> comments = postEntity.getComments().stream()
            .map(comment -> CommentDto.builder()
                .commentId(comment.getCommentId())
                .nickname(comment.getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .isOwner(currentUserNickname != null && comment.getNickname().equals(currentUserNickname))
                .build())
            .collect(Collectors.toList());

        return PostDetailResponseDto.builder()
            .postId(postEntity.getPostId())
            .nickname(postEntity.getNickname())
            .title(postEntity.getTitle())
            .content(postEntity.getContent())
            .createdAt(postEntity.getCreatedAt())
            .updatedAt(postEntity.getUpdatedAt())
            .viewCount(postEntity.getViewCount())
            .likeCount(postEntity.getLikeCount())
            .comments(comments)
            .build();
    }
}