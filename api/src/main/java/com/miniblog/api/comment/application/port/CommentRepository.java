package com.miniblog.api.comment.application.port;

import com.miniblog.api.comment.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Optional<Comment> findById(Long id);
    Optional<Comment> findWithMemberById(Long id);
    Comment save(Comment comment);
    void delete(Comment comment);
    void deleteAllByPostId(Long postId);
    void detachMemberByMemberId(Long memberId);
    void deleteAllByPostIds(List<Long> postIds);
}
