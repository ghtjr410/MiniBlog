package com.miniblog.api.member.infrastructure.persistence;

import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface EmailCodeJpaRepository extends JpaRepository<EmailCode, Long> {
    Optional<EmailCode> findByEmailAndType(String email, EmailCodeType type);
    void deleteByEmailAndType(String email, EmailCodeType type);

    @Modifying
    @Query("DELETE FROM EmailCode e WHERE e.expiresDate < :now")
    void deleteExpiredCodes(@Param("now") LocalDateTime now);
}
