package com.miniblog.api.comment.infrastructure;

import com.miniblog.api.comment.application.port.CommentRepository;
import com.miniblog.api.comment.domain.Comment;
import com.miniblog.api.comment.infrastructure.persistence.CommentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJpaRepository.findById(id);
    }

    @Override
    public Optional<Comment> findWithMemberById(Long id) {
        return commentJpaRepository.findWithMemberById(id);
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public void delete(Comment comment) {
        commentJpaRepository.delete(comment);
    }

    @Override
    public void deleteAllByPostId(Long postId) {
        commentJpaRepository.deleteAllByPostId(postId);
    }

    @Override
    public void detachMemberByMemberId(Long memberId) {
        commentJpaRepository.detachMemberByMemberId(memberId);
    }

    @Override
    public void deleteAllByPostIds(List<Long> postIds) {
        commentJpaRepository.deleteAllByPostIds(postIds);
    }
}
