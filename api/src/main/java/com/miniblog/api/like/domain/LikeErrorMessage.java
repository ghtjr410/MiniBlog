package com.miniblog.api.like.domain;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum LikeErrorMessage implements BaseMessage {
    ALREADY_LIKED("이미 좋아요한 게시글입니다."),
    LIKE_HISTORY_NOT_FOUND("좋아요 내역이 존재하지 않습니다.");

    private final String message;

    LikeErrorMessage(String message) {
        this.message = message;
    }
}
