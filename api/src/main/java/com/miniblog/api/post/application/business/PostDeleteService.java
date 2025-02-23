package com.miniblog.api.post.application.business;

import com.miniblog.api.comment.application.support.CommentBulkManager;
import com.miniblog.api.like.application.LikeBulkDeleter;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.post.application.support.PostReader;
import com.miniblog.api.post.application.support.PostStatisticsDeleter;
import com.miniblog.api.post.application.port.PostRepository;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostDeleteService {

    private final PostRepository postRepository;
    private final PostReader postQueryService;
    private final PostStatisticsDeleter postStatisticsDeleter;
    private final CommentBulkManager commentBulkManager;
    private final LikeBulkDeleter likeBulkDeleter;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * - 게시글 소유권 검증 후 삭제
     * - 관련 데이터(댓글, 좋아요, 통계) 삭제
     */
    public void deletePost(long postId, long memberId) {
        Post post = postQueryService.getById(postId);
        post.validateOwnership(memberId);

        // 게시글과 연결된 댓글, 좋아요 삭제
        commentBulkManager.deleteAllByPostId(postId);
        likeBulkDeleter.deleteAllByPostId(postId);

        // 게시글 통계 삭제 (조회수, 좋아요, 댓글 수 초기화)
        postStatisticsDeleter.deleteStatistics(postId);

        postRepository.delete(post);

        memberActivityEventPublisher.publishActivity(memberId);
    }
}
