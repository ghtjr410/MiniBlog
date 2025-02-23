package com.miniblog.api.like.infrastructure.persistence;

import com.miniblog.api.like.domain.LikeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LikeHistoryJpaRepository extends JpaRepository<LikeHistory, Long> {
    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
    Optional<LikeHistory> findByMemberIdAndPostId(Long memberId, Long postId);

    @Modifying
    @Query("DELETE FROM LikeHistory l WHERE l.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("DELETE FROM LikeHistory l WHERE l.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("DELETE FROM LikeHistory l WHERE l.member.id IN :postIds")
    void deleteAllByPostIds(@Param("postIds")List<Long> postIds);
}
