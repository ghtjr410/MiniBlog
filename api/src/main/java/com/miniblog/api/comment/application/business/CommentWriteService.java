package com.miniblog.api.comment.application.business;

import com.miniblog.api.comment.application.dto.CommentWriteData;
import com.miniblog.api.comment.application.port.CommentRepository;
import com.miniblog.api.comment.domain.Comment;
import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.application.support.PostReader;
import com.miniblog.api.post.application.support.PostStatisticsUpdater;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentWriteService {

    private final MemberReader memberReader;
    private final PostReader postReader;
    private final PostStatisticsUpdater postStatisticsUpdater;
    private final CommentRepository commentRepository;
    private final ClockHolder clockHolder;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * 새로운 댓글을 작성하고, 해당 게시글의 댓글 개수를 증가
     */
    @Transactional
    public long addComment(CommentWriteData data) {
        Member member = memberReader.getById(data.memberId());
        Post post = postReader.getById(data.postId());

        Comment comment = Comment.create(data.content(), clockHolder.now(), member, post);
        commentRepository.save(comment);

        postStatisticsUpdater.incrementCommentCount(post.getId());

        memberActivityEventPublisher.publishActivity(member.getId());
        return comment.getId();
    }
}
