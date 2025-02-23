package com.miniblog.api.member.domain;

import com.miniblog.api.common.domain.exception.ResourceExpiredException;
import com.miniblog.api.common.domain.exception.ResourceInvalidException;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

import static com.miniblog.api.member.domain.MemberErrorMessage.*;

@Builder(toBuilder = true)
@Getter
@Entity
@Table(
        name = "email_code",
        indexes = {
                @Index(name = "idx_email_type", columnList = "email, type"),
                @Index(name = "idx_expires_date", columnList = "expires_date")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EmailCode {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_code_id")
    private Long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EmailCodeType type;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private EmailCodeStatus status;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "expiresData", nullable = false, updatable = false)
    private LocalDateTime expiresDate;

    private static final long EXPIRY_MINUTES = 3;

    public static EmailCode create(String email, EmailCodeType type, String code, LocalDateTime now) {
        return EmailCode.builder()
                .email(email)
                .code(code)
                .type(type)
                .status(EmailCodeStatus.PENDING)
                .createdDate(now)
                .expiresDate(now.plusMinutes(EXPIRY_MINUTES))
                .build();
    }

    public void verify(String code, EmailCodeType type, LocalDateTime now) {
        assertCorrectCode(code);
        ensureCorrectType(type);
        assertExpired(now);
        markedVerified();
    }

    public void ensureVerified() {
        if (this.status != EmailCodeStatus.VERIFIED) {
            throw new ResourceInvalidException(EMAIL_NOT_VERIFIED);
        }
    }

    private void ensureCorrectType(EmailCodeType type) {
        if (this.type != type) {
            throw new ResourceInvalidException(EMAIL_CODE_INVALID);
        }
    }

    private void assertCorrectCode(String inputCode) {
        if (!StringUtils.equals(this.code, inputCode)) {
            throw new ResourceInvalidException(EMAIL_CODE_INVALID);
        }
    }

    private void assertExpired(LocalDateTime now) {
        if (expiresDate.isBefore(now)) {
            throw new ResourceExpiredException(EMAIL_CODE_EXPIRED);
        }
    }

    private void markedVerified() {
        this.status = EmailCodeStatus.VERIFIED;
    }

}
