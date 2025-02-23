package com.miniblog.api.member.domain;

import com.miniblog.api.common.domain.BaseType;

public enum EmailCodeType implements BaseType {
    EMAIL_VERIFICATION_CODE,
    PASSWORD_RESET_CODE,
    USERNAME_RECOVERY_CODE
}
