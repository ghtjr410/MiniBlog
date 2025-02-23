package com.miniblog.api.member.application.business;

import com.miniblog.api.auth.application.support.TokenBulkRevoker;
import com.miniblog.api.auth.application.support.TokenFinder;
import com.miniblog.api.auth.application.support.TokenValidator;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.comment.application.support.CommentBulkManager;
import com.miniblog.api.like.application.LikeBulkDeleter;
import com.miniblog.api.member.application.dto.DeleteAccountData;
import com.miniblog.api.member.application.port.MemberRepository;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.application.support.PostBulkDeleter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteAccountService {

    private final TokenFinder tokenFinder;
    private final TokenValidator tokenValidator;
    private final MemberReader memberReader;
    private final PostBulkDeleter postBulkDeleter;
    private final CommentBulkManager commentBulkManager;
    private final LikeBulkDeleter likeBulkDeleter;
    private final TokenBulkRevoker tokenBulkRevoker;
    private final MemberRepository memberRepository;

    /**
     * 회원 탈퇴 처리
     */
    public void deleteAccount(DeleteAccountData data) {
        // 요청된 토큰을 찾고 비교 검증
        RefreshToken refreshToken = tokenFinder.findByToken(data.refreshToken());
        tokenValidator.validate(refreshToken, data.refreshToken(), data.deviceInfo());

        // 회원 조회
        Member member = memberReader.getById(data.memberId());

        // 사용자가 작성한 게시글 및 관련 데이터 삭제
        postBulkDeleter.deleteAll(member.getId());

        // 회원이 남긴 댓글 및 좋아요 데이터 비활성화 및 삭제
        commentBulkManager.detachAllByMemberId(member.getId());
        likeBulkDeleter.deleteAllByMemberId(member.getId());

        // 사용자의 모든 RefreshToken을 조회 후 폐기
        tokenBulkRevoker.revokeAll(member.getId());

        // 회원 정보 삭제
        memberRepository.delete(member);
    }
}
