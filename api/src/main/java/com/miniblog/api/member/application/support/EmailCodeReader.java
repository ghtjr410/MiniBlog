package com.miniblog.api.member.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.member.application.port.EmailCodeRepository;
import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.miniblog.api.member.domain.MemberErrorMessage.*;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmailCodeReader {

    private final EmailCodeRepository emailCodeRepository;

    /**
     * 이메일 및 코드 타입으로 인증 코드 조회
     */
    public EmailCode getByEmailAndType(String email, EmailCodeType type) {
        return emailCodeRepository.findByEmailAndType(email, type)
                .orElseThrow(() -> new ResourceNotFoundException(EMAIL_NOT_FOUND));
    }
}
