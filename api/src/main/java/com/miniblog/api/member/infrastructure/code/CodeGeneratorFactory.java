package com.miniblog.api.member.infrastructure.code;

import com.miniblog.api.common.domain.exception.UnsupportedTypeException;
import com.miniblog.api.member.domain.EmailCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.miniblog.api.member.infrastructure.mail.MailErrorMessage.UNSUPPORTED_CODE_TYPE;

@Component
@RequiredArgsConstructor
public class CodeGeneratorFactory {

    private final EmailVerificationCodeGenerator emailVerificationCodeGenerator;
    private final MemberSecurityCodeGenerator passwordSecurityCodeGenerator;

    /**
     * 주어진 코드 타입에 따라 인증 코드 생성
     */
    public String generateCode(EmailCodeType type) {
        return switch (type) {
            case EMAIL_VERIFICATION_CODE -> emailVerificationCodeGenerator.generateCode();
            case PASSWORD_RESET_CODE, USERNAME_RECOVERY_CODE -> passwordSecurityCodeGenerator.generateCode();
            default -> throw new UnsupportedTypeException(UNSUPPORTED_CODE_TYPE, type);
        };
    }
}
