package com.miniblog.back.comment.listener;

import com.miniblog.back.comment.model.Comment;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class CommentListener {

    @PrePersist
    public void prePersist(Comment comment) {
        LocalDateTime localDateTime = LocalDateTime.now();
        if (comment.getCreatedDate() == null) {
            comment.setCreatedDate(localDateTime);
        }
        if (comment.getUpdatedDate() == null) {
            comment.setUpdatedDate(localDateTime);
        }
    }
}
