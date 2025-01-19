package com.miniblog.back.like.repository;

import com.miniblog.back.like.model.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long>, LikeHistoryRepositoryCustom {
    Optional<LikeHistory> findByPostIdAndMemberId(Long postId, Long memberId);
}
