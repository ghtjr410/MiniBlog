package com.miniblog.back.member.repository;

import com.miniblog.back.member.model.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long>, EmailVerificationRepositoryCustom {
    Optional<EmailVerification> findByUsernameAndEmail(String username, String email);
}
