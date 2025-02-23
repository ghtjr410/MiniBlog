package com.miniblog.api.post.application.port;

import com.miniblog.api.post.domain.PostViews;

import java.util.List;

public interface PostViewsRepository {
    PostViews save(PostViews postViews);
    void deleteByPostId(Long postId);
    void deleteAllByPostIds(List<Long> postIds);
}
