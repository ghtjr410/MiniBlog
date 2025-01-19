package com.miniblog.back.post.listener;

import com.miniblog.back.post.model.PostAggregate;
import jakarta.persistence.PrePersist;

public class PostAggregateListener {

    @PrePersist
    public void prePersist(PostAggregate postAggregate) {
        if (postAggregate.getComment_count() == null) {
            postAggregate.setComment_count(0L);
        }
        if (postAggregate.getLike_count() == null) {
            postAggregate.setLike_count(0L);
        }
    }
}
