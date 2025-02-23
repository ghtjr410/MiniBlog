package com.miniblog.api.like.application;

import com.miniblog.api.like.application.port.LikeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LikeBulkDeleter {
    private final LikeHistoryRepository likeHistoryRepository;

    /**
     * 특정 게시글의 좋아요 기록 일괄 삭제
     */
    @Transactional
    public void deleteAllByPostId(long postId) {
        likeHistoryRepository.deleteAllByPostId(postId);
    }

    /**
     * 특정 사용자의 모든 좋아요 기록 삭제
     */
    @Transactional
    public void deleteAllByMemberId(long memberId) {
        likeHistoryRepository.deleteAllByMemberId(memberId);
    }

    /**
     * 여러 게시글의 좋아요 기록 일괄 삭제
     */
    @Transactional
    public void deleteAllInPostIds(List<Long> postIds) {
        likeHistoryRepository.deleteAllByPostIds(postIds);
    }
}
