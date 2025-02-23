package com.miniblog.api.query.dto.query;

import com.miniblog.api.comment.domain.Comment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentDetailDto {
    private Long commentId;
    private String content;
    private AuthorDto author;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    @QueryProjection
    public CommentDetailDto(Long commentId, String content, AuthorDto author, LocalDateTime createdDate, LocalDateTime updatedDate) {
        this.commentId = commentId;
        this.content = content;
        this.author = author;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static CommentDetailDto from(Comment comment) {
        return new CommentDetailDto(
                comment.getId(),
                comment.getContent(),
                new AuthorDto(
                        comment.getMember().getId(),
                        comment.getMember().getNickname()
                ),
                comment.getCreatedDate(),
                comment.getUpdatedDate()
        );
    }
}
