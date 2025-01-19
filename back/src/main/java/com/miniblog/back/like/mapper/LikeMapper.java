package com.miniblog.back.like.mapper;

import com.miniblog.back.like.model.LikeHistory;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.post.model.Post;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public LikeHistory create(Long memberId, Long postId)  {
        return LikeHistory.builder()
                .member(Member.builder().id(memberId).build())
                .post(Post.builder().id(postId).build())
                .build();
    }
}
