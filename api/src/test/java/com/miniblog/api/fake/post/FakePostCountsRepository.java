package com.miniblog.api.fake.post;

import com.miniblog.api.post.application.port.PostCountsRepository;
import com.miniblog.api.post.domain.PostCounts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FakePostCountsRepository implements PostCountsRepository {
    private final Map<Long, PostCounts> postCountsStore = new ConcurrentHashMap<>();

    @Override
    public PostCounts save(PostCounts postCounts) {
        postCountsStore.put(postCounts.getId(), postCounts);
        return postCounts;
    }

    @Override
    public void incrementCommentCount(Long postId) {
        postCountsStore.computeIfPresent(postId, (id, counts) ->
                PostCounts.create(counts.getPost()).toBuilder()
                        .id(id)
                        .commentCount(counts.getCommentCount() + 1)
                        .likeCount(counts.getLikeCount())
                        .build()
        );
    }

    @Override
    public void incrementLikeCount(Long postId) {
        postCountsStore.computeIfPresent(postId, (id, counts) ->
                PostCounts.create(counts.getPost()).toBuilder()
                        .id(id)
                        .commentCount(counts.getCommentCount())
                        .likeCount(counts.getLikeCount() + 1)
                        .build()
        );
    }

    @Override
    public void decrementCommentCount(Long postId) {
        postCountsStore.computeIfPresent(postId, (id, counts) ->
                PostCounts.create(counts.getPost()).toBuilder()
                        .id(id)
                        .commentCount(Math.max(0, counts.getCommentCount() - 1))
                        .likeCount(counts.getLikeCount())
                        .build()
        );
    }

    @Override
    public void decrementLikeCount(Long postId) {
        postCountsStore.computeIfPresent(postId, (id, counts) ->
                PostCounts.create(counts.getPost()).toBuilder()
                        .id(id)
                        .commentCount(counts.getCommentCount())
                        .likeCount(Math.max(0, counts.getLikeCount() - 1))
                        .build()
        );
    }

    @Override
    public void deleteByPostId(Long postId) {
        postCountsStore.remove(postId);
    }

    @Override
    public void deleteAllByPostIds(List<Long> postIds) {
        postIds.forEach(postCountsStore::remove);
    }
}
