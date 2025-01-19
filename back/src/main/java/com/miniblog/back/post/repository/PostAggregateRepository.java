package com.miniblog.back.post.repository;

import com.miniblog.back.post.model.PostAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostAggregateRepository extends JpaRepository<PostAggregate, Long>, PostAggregateRepositoryCustom {
    @Modifying
    @Query("UPDATE PostAggregate p SET p.comment_count = p.comment_count + 1 WHERE p.post.id = :postId")
    void incrementCommentCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostAggregate p SET p.like_count = p.like_count + 1 WHERE p.post.id = :postId")
    void incrementLikeCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostAggregate p SET p.comment_count = p.comment_count - 1 WHERE p.post.id = :postId AND p.comment_count > 0")
    void decrementCommentCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE PostAggregate p SET p.like_count = p.like_count - 1 WHERE p.post.id = :postId AND p.like_count > 0")
    void decrementLikeCount(@Param("postId") Long postId);

    void deleteByPostId(Long postId);
}
