package com.miniblog.api.post.application.support;

import com.miniblog.api.post.application.port.PostCountsRepository;
import com.miniblog.api.post.application.port.PostViewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 게시글 통계를 삭제하는 컴포넌트
 * - 게시글 삭제 시 조회수, 좋아요, 댓글 수 관련 데이터를 함께 삭제
 */
@Component
@RequiredArgsConstructor
public class PostStatisticsDeleter {

    private final PostCountsRepository postCountsRepository;
    private final PostViewsRepository postViewsRepository;

    /**
     * 특정 게시글의 통계를 삭제
     */
    @Transactional
    public void deleteStatistics(long postId) {
        postCountsRepository.deleteByPostId(postId);
        postViewsRepository.deleteByPostId(postId);
    }

    /**
     * 여러 게시글의 통계를 한 번에 삭제
     */
    @Transactional
    public void deleteAllInPostIds(List<Long> postIds) {
        postCountsRepository.deleteAllByPostIds(postIds);
        postViewsRepository.deleteAllByPostIds(postIds);
    }
}
