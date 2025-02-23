package com.miniblog.api.member.domain;

import com.miniblog.api.common.domain.BaseMessage;
import lombok.Getter;

@Getter
public enum MemberErrorMessage implements BaseMessage {
    MEMBER_NOT_FOUND("회원 정보를 찾을 수 없습니다."),
    EMAIL_NOT_FOUND("해당 이메일이 존재하지 않습니다."),
    USERNAME_DUPLICATE("해당 사용자명이 이미 존재합니다."),
    NICKNAME_DUPLICATE("해당 닉네임이 이미 존재합니다."),
    EMAIL_DUPLICATE( "해당 이메일이 이미 존재합니다."),
    EMAIL_NOT_VERIFIED("이메일이 인증되지 않았습니다."),
    EMAIL_CODE_INVALID("잘못된 이메일 인증 코드입니다."),
    EMAIL_CODE_EXPIRED("만료된 이메일 인증 코드입니다.");

    private final String message;

    MemberErrorMessage(String message) {
        this.message = message;
    }
}
