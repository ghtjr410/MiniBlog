package com.miniblog.api.member.infrastructure;

import com.miniblog.api.member.application.port.EmailCodeRepository;
import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;
import com.miniblog.api.member.infrastructure.persistence.EmailCodeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailCodeRepositoryImpl implements EmailCodeRepository {
    private final EmailCodeJpaRepository emailCodeJpaRepository;

    @Override
    public Optional<EmailCode> findByEmailAndType(String email, EmailCodeType type) {
        return emailCodeJpaRepository.findByEmailAndType(email, type);
    }

    @Override
    public EmailCode save(EmailCode emailCode) {
        return emailCodeJpaRepository.save(emailCode);
    }

    @Override
    public void delete(EmailCode emailCode) {
        emailCodeJpaRepository.delete(emailCode);
    }

    @Override
    public void deleteExpiredCodes(LocalDateTime now) {
        emailCodeJpaRepository.deleteExpiredCodes(now);
    }

    @Override
    public void deleteByEmailAndType(String email, EmailCodeType type) {
        emailCodeJpaRepository.deleteByEmailAndType(email, type);
    }
}
