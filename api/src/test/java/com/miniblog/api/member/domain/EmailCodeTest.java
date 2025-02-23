package com.miniblog.api.member.domain;

import com.miniblog.api.common.domain.exception.ResourceExpiredException;
import com.miniblog.api.common.domain.exception.ResourceInvalidException;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmailCodeTest {

    private final String email = "test@test.com";
    private final EmailCodeType type = EmailCodeType.EMAIL_VERIFICATION_CODE;
    private final String code = "1q2w3e4r";
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void 이메일_코드를_정상적으로_발급한다() {
        // Given

        // When
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // Then
        assertThat(emailCode.getId()).isNull();
        assertThat(emailCode.getEmail()).isEqualTo(email);
        assertThat(emailCode.getType()).isEqualTo(type);
        assertThat(emailCode.getStatus()).isEqualTo(EmailCodeStatus.PENDING);
        assertThat(emailCode.getCreatedDate()).isEqualTo(now);
        assertThat(emailCode.getExpiresDate()).isEqualTo(now.plusMinutes(3));
    }
    @Test
    void 유효한_이메일_코드라면_검증에_성공하면_VERIFIED_상태로_변경된다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then
        assertThatCode(() -> emailCode.verify(code, EmailCodeType.EMAIL_VERIFICATION_CODE, now))
                .doesNotThrowAnyException();
        assertThat(emailCode.getStatus()).isEqualTo(EmailCodeStatus.VERIFIED);
    }

    @Test
    void 잘못된_코드를_입력하면_검증에_실패한다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then
        assertThatThrownBy(() -> emailCode.verify("wrongCode", type, now))
                .isInstanceOf(ResourceInvalidException.class);
    }

    @Test
    void 다른_타입의_코드를_입력하면_검증에_실패한다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then
        assertThatThrownBy(() -> emailCode.verify(code, EmailCodeType.PASSWORD_RESET_CODE, now))
                .isInstanceOf(ResourceInvalidException.class);
    }

    @Test
    void 만료된_코드를_입력하면_검증에_실패한다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then
        assertThatThrownBy(() -> emailCode.verify(code, type, now.plusMinutes(5)))
                .isInstanceOf(ResourceExpiredException.class);
    }

    @Test
    void 이메일_검증과정에서_실패하면_검증상태가_여전히_PENDING_이어야_한다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then (잘못된 코드 입력)
        assertThatThrownBy(() -> emailCode.verify("wrongCode", type, now))
                .isInstanceOf(ResourceInvalidException.class);
        assertThat(emailCode.getStatus()).isEqualTo(EmailCodeStatus.PENDING);

        // When & Then (잘못된 타입 입력)
        assertThatThrownBy(() -> emailCode.verify(code, EmailCodeType.PASSWORD_RESET_CODE, now))
                .isInstanceOf(ResourceInvalidException.class);
        assertThat(emailCode.getStatus()).isEqualTo(EmailCodeStatus.PENDING);

        // When & Then (만료된 코드 입력)
        assertThatThrownBy(() -> emailCode.verify(code, type, now.plusMinutes(5)))
                .isInstanceOf(ResourceExpiredException.class);
        assertThat(emailCode.getStatus()).isEqualTo(EmailCodeStatus.PENDING);
    }

    @Test
    void 검증되지_않은_이메일_코드를_검증됐는지_확인할_경우_실패한다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then
        assertThatThrownBy(emailCode::ensureVerified)
                .isInstanceOf(ResourceInvalidException.class);
    }

    @Test
    void 여러번_검증을_시도해도_VERIFIED_상태가_유지된다() {
        // Given
        EmailCode emailCode = EmailCode.create(email, type, code, now);

        // When & Then
        assertThatCode(() -> emailCode.verify(code, EmailCodeType.EMAIL_VERIFICATION_CODE, now))
                .doesNotThrowAnyException();
        assertThatCode(() -> emailCode.verify(code, EmailCodeType.EMAIL_VERIFICATION_CODE, now))
                .doesNotThrowAnyException();

        assertThat(emailCode.getStatus()).isEqualTo(EmailCodeStatus.VERIFIED);
    }
}