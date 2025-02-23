package com.miniblog.api.comment.infrastructure.persistence;

import com.miniblog.api.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentJpaRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.member WHERE c.id = :commentId")
    Optional<Comment> findWithMemberById(@Param("commentId") Long commentId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE Comment c SET c.member = NULL WHERE c.member.id = :memberId")
    void detachMemberByMemberId(@Param("memberId") Long memberId);

    @Modifying
    @Query("DELETE FROM Comment c WHERE c.post.id IN :postId")
    void deleteAllByPostIds(@Param("postId") List<Long> postIds);
}
