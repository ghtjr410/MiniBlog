package com.miniblog.api.query.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostDetailDto {
    private Long postId;
    private String title;
    private String content;
    private AuthorDto author;
    private Long viewCount;
    private Long commentCount;
    private Long likeCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @QueryProjection
    public PostDetailDto(Long postId, String title, String content, AuthorDto author, Long viewCount, Long commentCount, Long likeCount, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static PostDetailDto of() {
        return new PostDetailDto();
    }
}
