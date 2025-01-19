package com.miniblog.back.post.listener;

import com.miniblog.back.post.model.Post;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class PostListener {

    @PrePersist
    public void prePersist(Post post) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (post.getCreatedDate() == null) {
            post.setCreatedDate(localDateTime);
        }
        if (post.getUpdatedDate() == null) {
            post.setUpdatedDate(localDateTime);
        }
    }
}
