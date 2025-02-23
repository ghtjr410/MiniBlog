package com.miniblog.api.like.application;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.common.domain.exception.ResourceDuplicateException;
import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.like.application.port.LikeHistoryRepository;
import com.miniblog.api.like.domain.LikeHistory;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.application.support.PostReader;
import com.miniblog.api.post.application.support.PostStatisticsUpdater;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.like.domain.LikeErrorMessage.ALREADY_LIKED;
import static com.miniblog.api.like.domain.LikeErrorMessage.LIKE_HISTORY_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class LikeToggleService {

    private final MemberReader memberReader;
    private final PostReader postReader;
    private final PostStatisticsUpdater postStatisticsUpdater;
    private final ClockHolder clockHolder;
    private final LikeHistoryRepository likeHistoryRepository;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * 사용자가 특정 게시글에 좋아요 추가
     */
    @Transactional
    public void likePost(long postId, long memberId) {
        if (!likeHistoryRepository.existsByMemberIdAndPostId(memberId, postId)) {
            throw new ResourceDuplicateException(ALREADY_LIKED);
        }

        Member member = memberReader.getById(memberId);
        Post post = postReader.getById(postId);

        LikeHistory likeHistory = LikeHistory.create(member, post, clockHolder.now());
        likeHistoryRepository.save(likeHistory);

        postStatisticsUpdater.incrementLikeCount(post.getId());
        memberActivityEventPublisher.publishActivity(member.getId());
    }

    /**
     * 사용자가 특정 게시글에 좋아요 취소
     */
    @Transactional
    public void unlikePost(long postId, long memberId) {
        LikeHistory likeHistory = likeHistoryRepository.findByMemberIdAndPostId(memberId, postId)
                .orElseThrow(() -> new ResourceNotFoundException(LIKE_HISTORY_NOT_FOUND));

        likeHistoryRepository.delete(likeHistory);

        postStatisticsUpdater.decrementLikeCount(postId);
        memberActivityEventPublisher.publishActivity(memberId);
    }
}
