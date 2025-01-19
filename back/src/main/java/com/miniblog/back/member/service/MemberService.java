package com.miniblog.back.member.service;

import com.miniblog.back.auth.dto.internal.RefreshTokenDTO;
import com.miniblog.back.auth.repository.TokenRepository;
import com.miniblog.back.auth.service.BlacklistTokenService;
import com.miniblog.back.auth.token.TokenParser;
import com.miniblog.back.auth.util.TokenUtils;
import com.miniblog.back.comment.repository.CommentRepository;
import com.miniblog.back.like.repository.LikeHistoryRepository;
import com.miniblog.back.member.dto.request.*;
import com.miniblog.back.member.dto.response.AvailabilityResponseDTO;
import com.miniblog.back.member.dto.response.RegisterResponseDTO;
import com.miniblog.back.member.mapper.MemberMapper;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.repository.EmailVerificationRepository;
import com.miniblog.back.member.repository.MemberRepository;
import com.miniblog.back.member.validator.MemberValidator;
import com.miniblog.back.post.repository.PostAggregateRepository;
import com.miniblog.back.post.repository.PostRepository;
import com.miniblog.back.viewcount.repository.ViewCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final TokenRepository tokenRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final PostAggregateRepository postAggregateRepository;
    private final ViewCountRepository viewCountRepository;
    private final CommentRepository commentRepository;
    private final LikeHistoryRepository likeHistoryRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;
    private final BlacklistTokenService blacklistTokenService;
    private final MemberValidator memberValidator;
    private final MemberMapper memberMapper;
    private final TokenParser tokenParser;


    @Transactional(readOnly = true)
    public AvailabilityResponseDTO isUsernameAvailable(String username) {
        UsernameAvailableRequestDTO.of(username);
        memberValidator.validateUsername(username);
        return AvailabilityResponseDTO.of(true);
    }

    @Transactional(readOnly = true)
    public AvailabilityResponseDTO isNicknameAvailable(String nickname) {
        NicknameAvailableRequestDTO.of(nickname);
        memberValidator.validateNickname(nickname);
        return AvailabilityResponseDTO.of(true);
    }

    @Transactional
    public RegisterResponseDTO registerMember(RegisterRequestDTO requestDTO) {
        String username = requestDTO.username();
        String nickname = requestDTO.nickname();
        String email = requestDTO.email();
        String code = requestDTO.code();

        emailService.validateEmailVerification(username, email, code);
        memberValidator.validateForRegistration(username, nickname, email);

        Member member = memberMapper.create(requestDTO);
        memberRepository.save(member);

        emailVerificationRepository.deleteVerification(username, email, code);

        return RegisterResponseDTO.of(nickname);
    }

    @Transactional
    public void deleteMember(String authorizationHeader) {
        String refreshToken = TokenUtils.extractToken(authorizationHeader);
        Long memberId = tokenParser.getUserInfo(refreshToken).id();

        List<RefreshTokenDTO> refreshTokens = tokenRepository.findTokensByMemberId(memberId);

        refreshTokens.stream()
                .map(RefreshTokenDTO::refreshToken)
                .forEach(blacklistTokenService::addToBlacklist);

        tokenRepository.deleteByMemberId(memberId);

        List<Long> postIds = postRepository.findPostIdsByMemberId(memberId);

        if (postIds.isEmpty()) {
            memberRepository.deleteById(memberId);
            return;
        }

        postAggregateRepository.deleteByPostIds(postIds);
        viewCountRepository.deleteByPostIds(postIds);
        commentRepository.deleteByPostIdsOrMemberId(postIds, memberId);
        likeHistoryRepository.deleteByPostIdsOrMemberId(postIds, memberId);
        postRepository.deletePostsByIds(postIds);
        memberRepository.deleteById(memberId);
    }
}