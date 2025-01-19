package com.miniblog.back.member.listener;

import com.miniblog.back.member.model.EmailVerification;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class EmailVerificationListener {

    @PrePersist
    public void prePersist(EmailVerification emailVerification) {
        if (emailVerification.getExpiresDate() == null) {
            emailVerification.setExpiresDate(LocalDateTime.now().plusMinutes(5));
        }
    }

    @PreUpdate
    public void preUpdate(EmailVerification emailVerification) {
        emailVerification.setExpiresDate(LocalDateTime.now().plusMinutes(5));
    }
}
