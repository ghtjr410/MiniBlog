package com.miniblog.back.post.service;

import com.miniblog.back.auth.token.TokenParser;
import com.miniblog.back.comment.repository.CommentRepository;
import com.miniblog.back.common.dto.internal.DeleteConditionDTO;
import com.miniblog.back.like.repository.LikeHistoryRepository;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.common.exception.UnauthorizedException;
import com.miniblog.back.member.repository.MemberRepository;
import com.miniblog.back.post.dto.request.PostCreateRequestDTO;
import com.miniblog.back.post.dto.request.PostUpdateRequestDTO;
import com.miniblog.back.post.dto.response.PostCreatedResponseDTO;
import com.miniblog.back.post.dto.response.PostUpdatedResponseDTO;
import com.miniblog.back.post.mapper.PostAggregateMapper;
import com.miniblog.back.post.mapper.PostMapper;
import com.miniblog.back.post.model.Post;
import com.miniblog.back.post.repository.PostAggregateRepository;
import com.miniblog.back.post.repository.PostRepository;
import com.miniblog.back.viewcount.repository.ViewCountRepository;
import com.miniblog.back.viewcount.service.ViewCountService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final TokenParser tokenParser;
    private final PostRepository postRepository;
    private final ViewCountRepository viewCountRepository;
    private final CommentRepository commentRepository;
    private final LikeHistoryRepository likeHistoryRepository;
    private final PostAggregateRepository postAggregateRepository;
    private final ViewCountService viewCountService;
    private final PostAggregateService postAggregateService;
    private final PostMapper postMapper;
    private final EntityManager entityManager;

    @Transactional
    public PostCreatedResponseDTO create(
            String authorizationHeader,
            PostCreateRequestDTO requestDTO
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(token).id();

        Post post = postMapper.create(requestDTO, memberId);
        postRepository.save(post);
        postAggregateService.create(post);
        viewCountService.create(post);

        return PostCreatedResponseDTO.of(post);
    }

    @Transactional
    public PostUpdatedResponseDTO update(
            String authorizationHeader,
            PostUpdateRequestDTO requestDTO,
            Long postId
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(token).id();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        if (!postRepository.isPostOwnedByUser(postId, memberId)) {
            throw new UnauthorizedException("게시글의 소유자가 아닙니다.");
        }

        postMapper.update(post, requestDTO);
        postRepository.save(post);

        return PostUpdatedResponseDTO.of(post);
    }

    @Transactional
    public void delete(
            String authorizationHeader,
            Long postId
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long tokenMemberId = tokenParser.getUserInfo(token).id();

        Long postMemberId = postRepository.findMemberIdById(postId)
                .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));

        if (!tokenMemberId.equals(postMemberId)) {
            throw new UnauthorizedException("게시글의 소유자가 아닙니다.");
        }

        postAggregateRepository.deleteByPostId(postId);
        viewCountRepository.deleteByPostId(postId);
        commentRepository.deleteByCondition(DeleteConditionDTO.byPostId(postId));
        likeHistoryRepository.deleteByCondition(DeleteConditionDTO.byPostId(postId));

        entityManager.flush();

        postRepository.deleteByCondition(DeleteConditionDTO.byId(postId));
    }
}
