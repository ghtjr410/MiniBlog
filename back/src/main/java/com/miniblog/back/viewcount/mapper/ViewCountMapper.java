package com.miniblog.back.viewcount.mapper;

import com.miniblog.back.post.model.Post;
import com.miniblog.back.viewcount.model.ViewCount;
import org.springframework.stereotype.Component;

@Component
public class ViewCountMapper {
    public ViewCount create(Post post) {
        return ViewCount.builder()
                .post(post)
                .build();
    }
}
