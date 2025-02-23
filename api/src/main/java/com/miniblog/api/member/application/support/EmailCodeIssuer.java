package com.miniblog.api.member.application.support;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.port.EmailCodeRepository;
import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;
import com.miniblog.api.member.infrastructure.code.CodeGeneratorFactory;
import com.miniblog.api.member.infrastructure.mail.MailSenderFactory;
import com.miniblog.api.member.infrastructure.mail.MailSenderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailCodeIssuer {

    private final ClockHolder clockHolder;
    private final EmailCodeRepository emailCodeRepository;
    private final CodeGeneratorFactory codeGeneratorFactory;
    private final MailSenderFactory mailSenderFactory;

    /**
     * 이메일 인증 코드 발급 및 메일 전송
     */
    public void issueCode(String email, EmailCodeType codeType, MailSenderType mailType) {
        emailCodeRepository.deleteByEmailAndType(email, codeType);

        String code = codeGeneratorFactory.generateCode(codeType);
        EmailCode emailCode = EmailCode.create(email, codeType, code, clockHolder.now());
        emailCodeRepository.save(emailCode);

        mailSenderFactory.sendMail(email, emailCode.getCode(), mailType);
    }
}
