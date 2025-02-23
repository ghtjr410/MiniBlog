package com.miniblog.api.query.util;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum QueryErrorMessage implements BaseMessage {
    POST_DETAIL_NOT_FOUND("해당 게시글이 존재하지 않습니다.");

    private final String message;

    QueryErrorMessage(String message) {
        this.message = message;
    }
}
