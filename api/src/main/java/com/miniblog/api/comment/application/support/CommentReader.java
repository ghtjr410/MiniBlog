package com.miniblog.api.comment.application.support;

import com.miniblog.api.comment.application.port.CommentRepository;
import com.miniblog.api.comment.domain.Comment;
import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.comment.domain.CommentErrorMessage.*;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentReader {

    private final CommentRepository commentRepository;

    /**
     * 특정 ID의 댓글을 조회하고, 없을 경우 예외 발생
     */
    public Comment getByIdOrThrow(long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND));
    }

    /**
     * 특정 ID의 댓글과 작성자 정보를 함께 조회 (패치 조인 활용)
     */
    public Comment getByIdWithMemberOrThrow(long id) {
        return commentRepository.findWithMemberById(id)
                .orElseThrow(() -> new ResourceNotFoundException(COMMENT_NOT_FOUND));
    }

}
