package com.miniblog.api.member.infrastructure.mail;

import com.miniblog.api.common.domain.BaseType;
import lombok.Getter;

@Getter
public enum MailSenderType implements BaseType {
    EMAIL_VERIFICATION_MAIL("MiniBlog 회원가입 이메일 인증 코드"),
    PASSWORD_RESET_MAIL("MiniBlog 비밀번호 초기화 인증 코드"),
    USERNAME_RECOVERY_MAIL("MiniBlog 아이디 찾기 인증 코드"),
    PASSWORD_CHANGE_MAIL("MiniBlog 비밀번호 변경 알림");

    private final String subject;

    MailSenderType(String subject) {
        this.subject = subject;
    }
}
