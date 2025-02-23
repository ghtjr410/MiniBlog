package com.miniblog.api.member.application.port;

import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailCodeRepository {
    EmailCode save(EmailCode verificationCode);
    Optional<EmailCode> findByEmailAndType(String email, EmailCodeType type);
    void deleteByEmailAndType(String email, EmailCodeType type);
    void delete(EmailCode emailCode);
    void deleteExpiredCodes(LocalDateTime now);
}
