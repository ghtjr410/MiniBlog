package com.miniblog.back.post.mapper;

import com.miniblog.back.member.model.Member;
import com.miniblog.back.post.dto.request.PostCreateRequestDTO;
import com.miniblog.back.post.dto.request.PostUpdateRequestDTO;
import com.miniblog.back.post.model.Post;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostMapper {

    public Post create(PostCreateRequestDTO requestDTO, Long memberId) {
        return Post.builder()
                .title(requestDTO.title())
                .content(requestDTO.content())
                .member(Member.builder().id(memberId).build())
                .build();
    }

    public void update(Post post, PostUpdateRequestDTO requestDTO) {
        post.setTitle(requestDTO.title());
        post.setContent(requestDTO.content());
        post.setUpdatedDate(LocalDateTime.now());
    }
}
