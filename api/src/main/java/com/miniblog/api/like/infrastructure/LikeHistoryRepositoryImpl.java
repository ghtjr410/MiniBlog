package com.miniblog.api.like.infrastructure;

import com.miniblog.api.like.application.port.LikeHistoryRepository;
import com.miniblog.api.like.domain.LikeHistory;
import com.miniblog.api.like.infrastructure.persistence.LikeHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LikeHistoryRepositoryImpl implements LikeHistoryRepository {

    private final LikeHistoryJpaRepository likeHistoryJpaRepository;

    @Override
    public boolean existsByMemberIdAndPostId(Long memberId, Long postId) {
        return likeHistoryJpaRepository.existsByMemberIdAndPostId(memberId, postId);
    }

    @Override
    public Optional<LikeHistory> findByMemberIdAndPostId(Long memberId, Long postId) {
        return likeHistoryJpaRepository.findByMemberIdAndPostId(memberId, postId);
    }

    @Override
    public LikeHistory save(LikeHistory likeHistory) {
        return likeHistoryJpaRepository.save(likeHistory);
    }

    @Override
    public void delete(LikeHistory likeHistory) {
        likeHistoryJpaRepository.delete(likeHistory);
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        likeHistoryJpaRepository.deleteAllByPostId(postId);
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        likeHistoryJpaRepository.deleteAllByMemberId(memberId);
    }

    @Override
    public void deleteAllByPostIds(List<Long> postIds) {
        likeHistoryJpaRepository.deleteAllByPostIds(postIds);
    }
}
