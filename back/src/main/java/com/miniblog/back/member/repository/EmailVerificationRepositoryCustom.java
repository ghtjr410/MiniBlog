package com.miniblog.back.member.repository;

public interface EmailVerificationRepositoryCustom {
    boolean existsValidVerification(String username, String email, String code);
    void deleteVerification(String username, String email, String code);
}
