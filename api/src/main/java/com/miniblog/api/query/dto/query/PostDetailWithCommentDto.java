package com.miniblog.api.query.dto.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
public class PostDetailWithCommentDto {
    private PostDetailDto post;
    private Page<CommentDetailDto> comments;

    public PostDetailWithCommentDto(PostDetailDto post, Page<CommentDetailDto> comments) {
        this.post = post;
        this.comments = comments;
    }

    public static PostDetailWithCommentDto of(PostDetailDto post, Page<CommentDetailDto> comments) {
        return new PostDetailWithCommentDto(post, comments);
    }
}
