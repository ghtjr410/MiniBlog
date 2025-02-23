package com.miniblog.api.like.application.port;

import com.miniblog.api.like.domain.LikeHistory;

import java.util.List;
import java.util.Optional;

public interface LikeHistoryRepository {
    LikeHistory save(LikeHistory likeHistory);
    void delete(LikeHistory likeHistory);
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
    Optional<LikeHistory> findByMemberIdAndPostId(Long memberId, Long postId);
    void deleteAllByPostId(Long postId);
    void deleteAllByMemberId(Long memberId);
    void deleteAllByPostIds(List<Long> postIds);
}
