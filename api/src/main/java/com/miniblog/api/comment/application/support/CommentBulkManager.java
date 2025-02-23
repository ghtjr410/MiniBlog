package com.miniblog.api.comment.application.support;

import com.miniblog.api.comment.application.port.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommentBulkManager {

    private final CommentRepository commentRepository;

    /**
     * 특정 게시글의 모든 댓글을 삭제
     */
    @Transactional
    public void deleteAllByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
    }

    /**
     * 특정 사용자의 댓글에서 사용자 정보를 분리 (회원 탈퇴 시)
     */
    @Transactional
    public void detachAllByMemberId(Long memberId) {
        commentRepository.detachMemberByMemberId(memberId);
    }

    /**
     * 여러 게시글의 댓글을 일괄 삭제
     */
    @Transactional
    public void deleteAllInPostIds(List<Long> postIds) {
        commentRepository.deleteAllByPostIds(postIds);
    }
}
