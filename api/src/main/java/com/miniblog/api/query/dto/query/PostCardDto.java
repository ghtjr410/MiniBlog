package com.miniblog.api.query.dto.query;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class PostCardDto {
    private Long postId;
    private String title;
    private String contentSummary;
    private AuthorDto author;
    private Long viewCount;
    private Long commentCount;
    private Long likeCount;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @QueryProjection
    public PostCardDto(Long postId, String title, String contentSummary, AuthorDto author, Long viewCount, Long commentCount, Long likeCount, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.postId = postId;
        this.title = title;
        this.contentSummary = contentSummary;
        this.author = author;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
