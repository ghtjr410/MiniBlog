package com.miniblog.api.comment.domain;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum CommentErrorMessage implements BaseMessage {
    COMMENT_NOT_FOUND("해당 댓글을 찾을 수 없습니다."),
    COMMENT_NOT_OWNER("댓글의 소유자가 아닙니다.");

    private final String message;

    CommentErrorMessage(String message) {
        this.message = message;
    }
}
