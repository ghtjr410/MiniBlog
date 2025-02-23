package com.miniblog.api.post.application.support;

import com.miniblog.api.post.application.port.PostCountsRepository;
import com.miniblog.api.post.application.port.PostViewsCacheStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 통계를 업데이트하는 컴포넌트
 * - 댓글 및 좋아요 수를 증가/감소
 * - 조회수는 캐시를 통해 증가 처리
 */
@Component
@RequiredArgsConstructor
public class PostStatisticsUpdater {

    private final PostCountsRepository postCountsRepository;
    private final PostViewsCacheStore postViewsCacheStore;

    @Transactional
    public void incrementCommentCount(long postId) {
        postCountsRepository.incrementCommentCount(postId);
    }

    @Transactional
    public void incrementLikeCount(long postId) {
        postCountsRepository.incrementLikeCount(postId);
    }

    @Transactional
    public void decrementCommentCount(long postId) {
        postCountsRepository.decrementCommentCount(postId);
    }

    @Transactional
    public void decrementLikeCount(long postId) {
        postCountsRepository.decrementLikeCount(postId);
    }

    /**
     * 특정 게시글의 조회수를 캐시에 반영
     */
    public void incrementViewCount(long postId) {
        postViewsCacheStore.incrementViewCount(postId);
    }
}
