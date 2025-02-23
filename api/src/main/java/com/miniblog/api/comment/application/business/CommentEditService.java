package com.miniblog.api.comment.application.business;

import com.miniblog.api.comment.application.support.CommentReader;
import com.miniblog.api.comment.application.dto.CommentEditData;
import com.miniblog.api.comment.domain.Comment;
import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentEditService {

    private final CommentReader commentReader;
    private final ClockHolder clockHolder;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * 특정 댓글의 내용을 수정
     */
    @Transactional
    public void editComment(CommentEditData data) {
        Comment comment = commentReader.getByIdOrThrow(data.commentId());

        comment.validateOwnership(data.memberId());

        comment.updateContent(data.content(), clockHolder.now());

        memberActivityEventPublisher.publishActivity(comment.getMember().getId());
    }
}
