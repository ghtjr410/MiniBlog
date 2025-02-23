package com.miniblog.api.post.application.port;

import com.miniblog.api.post.domain.PostCounts;

import java.util.List;

public interface PostCountsRepository {
    PostCounts save(PostCounts postCounts);
    void incrementCommentCount(Long postId);
    void incrementLikeCount(Long postId);
    void decrementCommentCount(Long postId);
    void decrementLikeCount(Long postId);
    void deleteByPostId(Long postId);
    void deleteAllByPostIds(List<Long> postIds);
}
