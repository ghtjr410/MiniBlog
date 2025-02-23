package com.miniblog.api.comment.application.business;

import com.miniblog.api.comment.application.support.CommentReader;
import com.miniblog.api.comment.application.port.CommentRepository;
import com.miniblog.api.comment.domain.Comment;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.post.application.support.PostStatisticsUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {

    private final CommentReader commentReader;
    private final PostStatisticsUpdater postStatisticsUpdater;
    private final CommentRepository commentRepository;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * 사용자의 특정 댓글을 삭제하고, 해당 게시글의 댓글 개수를 감소
     */
    @Transactional
    public void deleteComment(long commentId, long memberId) {
        Comment comment = commentReader.getByIdOrThrow(commentId);

        comment.validateOwnership(memberId);
        long postId = comment.getPost().getId();

        commentRepository.delete(comment);
        postStatisticsUpdater.decrementCommentCount(postId);

        memberActivityEventPublisher.publishActivity(memberId);
    }
}
