package com.miniblog.back.member.mapper;

import com.miniblog.back.member.dto.request.EmailVerificationRequestDTO;
import com.miniblog.back.member.model.EmailVerification;
import org.springframework.stereotype.Component;

@Component
public class EmailVerificationMapper {
    public EmailVerification create(EmailVerificationRequestDTO requestDTO, String code) {
        return EmailVerification.builder()
                .username(requestDTO.username())
                .email(requestDTO.email())
                .code(code)
                .build();
    }

    public void update(EmailVerification emailVerification, String code) {
        emailVerification.setCode(code);
    }
}
