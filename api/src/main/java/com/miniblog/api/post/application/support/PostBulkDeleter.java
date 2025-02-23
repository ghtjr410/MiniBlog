package com.miniblog.api.post.application.support;

import com.miniblog.api.comment.application.support.CommentBulkManager;
import com.miniblog.api.like.application.LikeBulkDeleter;
import com.miniblog.api.post.application.port.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostBulkDeleter {

    private final PostReader postReader;
    private final PostRepository postRepository;
    private final CommentBulkManager commentBulkManager;
    private final LikeBulkDeleter likeBulkManager;
    private final PostStatisticsDeleter postStatisticsDeleter;

    /**
     * 여러 개의 게시글을 한 번에 삭제
     * 관련된 댓글, 좋아요, 통계 데이터를 먼저 삭제한 후 게시글 삭제
     */
    @Transactional
    public void deleteAll(long memberId) {
        List<Long> postIds = postReader.getIdsByMemberId(memberId);
        if (postIds.isEmpty()) return;

        commentBulkManager.deleteAllInPostIds(postIds);
        likeBulkManager.deleteAllInPostIds(postIds);

        postStatisticsDeleter.deleteAllInPostIds(postIds);

        postRepository.deleteAllByIdInBatch(postIds);
    }
}
