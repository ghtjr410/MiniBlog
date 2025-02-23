package com.miniblog.api.query.application;

import com.miniblog.api.query.dto.query.CommentDetailDto;
import com.miniblog.api.query.infrastructure.CommentDetailQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDetailQueryService {

    private final CommentDetailQueryRepository commentDetailQueryRepository;

    public Page<CommentDetailDto> getCommentsByPostId(Long postId, Pageable pageable) {
        return commentDetailQueryRepository.getCommentsByPostId(postId, pageable);
    }
}
