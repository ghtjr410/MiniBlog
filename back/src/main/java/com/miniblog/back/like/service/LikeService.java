package com.miniblog.back.like.service;

import com.miniblog.back.auth.token.TokenParser;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.like.mapper.LikeMapper;
import com.miniblog.back.common.dto.internal.DeleteConditionDTO;
import com.miniblog.back.common.dto.internal.UserInfoDTO;
import com.miniblog.back.common.exception.UnauthorizedException;
import com.miniblog.back.like.dto.request.ToggleLikeRequestDTO;
import com.miniblog.back.like.dto.response.ToggleLikeResponseDTO;
import com.miniblog.back.like.model.LikeHistory;
import com.miniblog.back.like.repository.LikeHistoryRepository;
import com.miniblog.back.post.repository.PostAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final TokenParser tokenParser;
    private final LikeHistoryRepository likeHistoryRepository;
    private final PostAggregateRepository postAggregateRepository;
    private final LikeMapper likeMapper;

    @Transactional
    public ToggleLikeResponseDTO toggleLike(
            String authorizationHeader,
            ToggleLikeRequestDTO requestDTO
    ) {
        String token = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(token).id();
        Long postId = requestDTO.postId();

        Optional<LikeHistory> optionalLikeHistory = likeHistoryRepository.findByPostIdAndMemberId(requestDTO.postId(), memberId);

        if (optionalLikeHistory.isPresent()) {
            deleteLikeHistory(optionalLikeHistory.get(), memberId, postId);
            return ToggleLikeResponseDTO.of(false);
        } else {
            createLikeHistory(memberId, postId);
            return ToggleLikeResponseDTO.of(true);
        }
    }

    private void deleteLikeHistory(LikeHistory existing, Long memberId, Long postId) {
        if (!likeHistoryRepository.isOwnedByUser(existing.getId(), memberId)) {
            throw new UnauthorizedException("좋아요의 소유자가 아닙니다.");
        }
        likeHistoryRepository.deleteByCondition(DeleteConditionDTO.byId(existing.getId()));
        postAggregateRepository.decrementLikeCount(postId);
    }

    private void createLikeHistory(Long memberId, Long postId) {
        LikeHistory likeHistory = likeMapper.create(memberId, postId);
        likeHistoryRepository.save(likeHistory);
        postAggregateRepository.incrementLikeCount(postId);
    }
}
