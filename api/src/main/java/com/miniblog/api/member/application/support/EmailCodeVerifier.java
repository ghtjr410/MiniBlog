package com.miniblog.api.member.application.support;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class EmailCodeVerifier {

    private final ClockHolder clockHolder;
    private final EmailCodeReader emailCodeReader;

    /**
     * 이메일 코드 검증
     */
    public void verifyCode(String email, String code, EmailCodeType codeType) {
        EmailCode emailCode = emailCodeReader.getByEmailAndType(email, codeType);
        emailCode.verify(code, codeType, clockHolder.now());
    }
}
