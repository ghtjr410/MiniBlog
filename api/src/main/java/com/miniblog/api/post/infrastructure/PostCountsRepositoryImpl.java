package com.miniblog.api.post.infrastructure;

import com.miniblog.api.post.application.port.PostCountsRepository;
import com.miniblog.api.post.domain.PostCounts;
import com.miniblog.api.post.infrastructure.persistence.PostCountsJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostCountsRepositoryImpl implements PostCountsRepository {
    private final PostCountsJpaRepository postCountsJpaRepository;

    @Override
    public PostCounts save(PostCounts postCounts) {
        return postCountsJpaRepository.save(postCounts);
    }

    @Override
    public void incrementCommentCount(Long postId) {
        postCountsJpaRepository.incrementCommentCount(postId);
    }

    @Override
    public void incrementLikeCount(Long postId) {
        postCountsJpaRepository.incrementLikeCount(postId);
    }

    @Override
    public void decrementCommentCount(Long postId) {
        postCountsJpaRepository.decrementCommentCount(postId);
    }

    @Override
    public void decrementLikeCount(Long postId) {
        postCountsJpaRepository.decrementLikeCount(postId);
    }

    @Override
    public void deleteByPostId(Long postId) {
        postCountsJpaRepository.deleteByPostId(postId);
    }

    @Override
    public void deleteAllByPostIds(List<Long> postIds) {
        postCountsJpaRepository.bulkDeleteByPostIds(postIds);
    }
}
