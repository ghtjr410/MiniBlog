package com.miniblog.api.post.infrastructure;

import com.miniblog.api.post.application.port.PostViewsCacheStore;
import com.miniblog.api.post.infrastructure.redis.PostViewsRedisStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class PostViewsCacheStoreImpl implements PostViewsCacheStore {

    private final PostViewsRedisStore postViewsRedisStore;

    @Override
    public void incrementViewCount(Long postId) {
        postViewsRedisStore.incrementViewCount(postId);
    }

    @Override
    public Map<Long, Integer> getAllViewCounts() {
        return postViewsRedisStore.getAllViewCounts();
    }

    @Override
    public void deleteViewCounts(Set<String> keys) {
        postViewsRedisStore.deleteViewCounts(keys);
    }
}
