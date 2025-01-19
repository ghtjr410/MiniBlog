package com.miniblog.back.comment.repository;

import com.miniblog.back.comment.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository <Comment, Long>, CommentRepositoryCustom {
}
