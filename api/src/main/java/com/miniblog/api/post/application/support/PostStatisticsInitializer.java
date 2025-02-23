package com.miniblog.api.post.application.support;

import com.miniblog.api.post.application.port.PostCountsRepository;
import com.miniblog.api.post.application.port.PostViewsRepository;
import com.miniblog.api.post.domain.Post;
import com.miniblog.api.post.domain.PostCounts;
import com.miniblog.api.post.domain.PostViews;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 게시글 통계를 초기화하는 컴포넌트
 * - 게시글이 생성될 때 조회수, 좋아요, 댓글 수를 0으로 설정
 */
@Component
@RequiredArgsConstructor
public class PostStatisticsInitializer {

    private final PostCountsRepository postCountsRepository;
    private final PostViewsRepository postViewsRepository;


    @Transactional
    public void initialize(Post post) {
        postCountsRepository.save(PostCounts.create(post));
        postViewsRepository.save(PostViews.create(post));
    }
}
