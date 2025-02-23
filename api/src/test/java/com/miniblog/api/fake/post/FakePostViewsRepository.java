package com.miniblog.api.fake.post;

import com.miniblog.api.post.application.port.PostViewsRepository;
import com.miniblog.api.post.domain.PostViews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakePostViewsRepository implements PostViewsRepository {
    private final Map<Long, PostViews> postViewsStore = new ConcurrentHashMap<>();

    @Override
    public PostViews save(PostViews postViews) {
        postViewsStore.put(postViews.getId(), postViews);
        return postViews;
    }

    @Override
    public void deleteByPostId(Long postId) {
        postViewsStore.remove(postId);
    }

    @Override
    public void deleteAllByPostIds(List<Long> postIds) {
        postIds.forEach(postViewsStore::remove);
    }
}
