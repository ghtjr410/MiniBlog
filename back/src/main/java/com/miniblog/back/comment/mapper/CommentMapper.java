package com.miniblog.back.comment.mapper;

import com.miniblog.back.comment.dto.request.CommentCreateRequestDTO;
import com.miniblog.back.comment.dto.request.CommentUpdateRequestDTO;
import com.miniblog.back.comment.model.Comment;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.post.model.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public Comment create(CommentCreateRequestDTO requestDTO, Long memberId) {
        return Comment.builder()
                .member(Member.builder().id(memberId).build())
                .post(Post.builder().id(requestDTO.postId()).build())
                .content(requestDTO.content())
                .build();
    }

    public void update(Comment comment, CommentUpdateRequestDTO requestDTO) {
        comment.setContent(requestDTO.content());
        comment.setUpdatedDate(LocalDateTime.now());
    }
}
