package com.miniblog.api.post.infrastructure;

import com.miniblog.api.post.application.port.PostViewsRepository;
import com.miniblog.api.post.domain.PostViews;
import com.miniblog.api.post.infrastructure.persistence.PostViewsJpaRepository;
import com.miniblog.api.post.infrastructure.redis.PostViewsRedisStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostViewsRepositoryImpl implements PostViewsRepository {
    private final PostViewsJpaRepository postViewsJpaRepository;

    // -- Jpa --
    @Override
    public PostViews save(PostViews postViews) {
        return postViewsJpaRepository.save(postViews);
    }

    @Override
    public void deleteByPostId(Long postId) {
        postViewsJpaRepository.deleteByPostId(postId);
    }

    @Override
    public void deleteAllByPostIds(List<Long> postIds) {
        postViewsJpaRepository.bulkDeleteByPostIds(postIds);
    }
}
