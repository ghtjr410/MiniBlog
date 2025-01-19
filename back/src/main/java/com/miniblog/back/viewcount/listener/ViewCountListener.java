package com.miniblog.back.viewcount.listener;

import com.miniblog.back.viewcount.model.ViewCount;
import jakarta.persistence.PrePersist;

public class ViewCountListener {
    @PrePersist
    public void prePersist(ViewCount viewCount) {
        if (viewCount.getCount() == null) {
            viewCount.setCount(0L);
        }
    }
}
