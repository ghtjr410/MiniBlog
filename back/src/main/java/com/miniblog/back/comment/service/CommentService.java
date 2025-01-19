package com.miniblog.back.comment.service;

import com.miniblog.back.auth.token.TokenParser;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.comment.dto.internal.PostIdAndMemberIdDTO;
import com.miniblog.back.comment.dto.request.CommentCreateRequestDTO;
import com.miniblog.back.comment.dto.request.CommentUpdateRequestDTO;
import com.miniblog.back.comment.dto.response.CommentCreatedResponseDTO;
import com.miniblog.back.comment.dto.response.CommentUpdatedResponseDTO;
import com.miniblog.back.comment.mapper.CommentMapper;
import com.miniblog.back.comment.model.Comment;
import com.miniblog.back.comment.repository.CommentRepository;
import com.miniblog.back.common.dto.internal.DeleteConditionDTO;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.common.exception.UnauthorizedException;
import com.miniblog.back.post.repository.PostAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final TokenParser tokenParser;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final PostAggregateRepository postAggregateRepository;

    @Transactional
    public CommentCreatedResponseDTO create(
            String authorizationHeader,
            CommentCreateRequestDTO requestDTO
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(token).id();

        Comment comment = commentMapper.create(requestDTO, memberId);
        commentRepository.save(comment);
        postAggregateRepository.incrementCommentCount(requestDTO.postId());

        return CommentCreatedResponseDTO.of(comment);
    }

    @Transactional
    public CommentUpdatedResponseDTO update(
            String authorizationHeader,
            CommentUpdateRequestDTO requestDTO,
            Long commentId
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(token).id();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        if (!commentRepository.isOwnedByUser(commentId, memberId)) {
            throw new UnauthorizedException("댓글의 소유자가 아닙니다.");
        }

        commentMapper.update(comment,requestDTO);
        commentRepository.save(comment);

        return CommentUpdatedResponseDTO.of(comment);
    }

    @Transactional
    public void delete(
            String authorizationHeader,
            Long commentId
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(token).id();

        PostIdAndMemberIdDTO postIdAndMemberId = commentRepository.findPostIdAndMemberIdById(commentId)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        if (!postIdAndMemberId.memberId().equals(memberId)) {
            throw new UnauthorizedException("댓글의 소유자가 아닙니다.");
        }

        commentRepository.deleteByCondition(DeleteConditionDTO.byId(commentId));
        postAggregateRepository.decrementCommentCount(postIdAndMemberId.postId());
    }
}
