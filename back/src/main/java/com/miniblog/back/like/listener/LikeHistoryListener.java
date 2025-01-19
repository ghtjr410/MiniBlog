package com.miniblog.back.like.listener;

import com.miniblog.back.like.model.LikeHistory;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

public class LikeHistoryListener {

    @PrePersist
    public void prePersist(LikeHistory likeHistory) {
        if (likeHistory.getCreatedDate() == null) {
            likeHistory.setCreatedDate(LocalDateTime.now());
        }
    }
}
