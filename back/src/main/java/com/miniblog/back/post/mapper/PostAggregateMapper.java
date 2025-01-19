package com.miniblog.back.post.mapper;

import com.miniblog.back.post.model.Post;
import com.miniblog.back.post.model.PostAggregate;
import org.springframework.stereotype.Component;

@Component
public class PostAggregateMapper {

    public PostAggregate create(Long postId) {
        return PostAggregate.builder()
                .post(Post.builder().id(postId).build())
                .build();
    }
}
