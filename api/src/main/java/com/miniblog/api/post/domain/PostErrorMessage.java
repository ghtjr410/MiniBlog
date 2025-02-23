package com.miniblog.api.post.domain;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum PostErrorMessage implements BaseMessage {
    POST_NOT_OWNER("게시글의 소유자가 아닙니다."),
    POST_NOT_FOUND("해당 게시글을 찾을 수 없습니다.");

    private final String message;

    PostErrorMessage(String message) {
        this.message = message;
    }
}
