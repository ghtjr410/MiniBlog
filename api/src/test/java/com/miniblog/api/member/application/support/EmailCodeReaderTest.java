package com.miniblog.api.member.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.fake.member.FakeEmailCodeRepository;
import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeStatus;
import com.miniblog.api.member.domain.EmailCodeType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmailCodeReaderTest {

    private FakeEmailCodeRepository fakeEmailCodeRepository;
    private EmailCodeReader emailCodeReader;

    @BeforeEach
    void setUp() {
        fakeEmailCodeRepository = new FakeEmailCodeRepository();
        emailCodeReader = new EmailCodeReader(fakeEmailCodeRepository);
    }

    private EmailCode createEmailCode(String mail, EmailCodeType codeType) {
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1,1,12,0,0);

        return EmailCode.builder()
                .id(1L)
                .email(mail)
                .code("123456")
                .type(codeType)
                .status(EmailCodeStatus.PENDING)
                .createdDate(fixedDateTime)
                .expiresDate(fixedDateTime.plusMinutes(3))
                .build();
    }

    @Test
    void 존재하는_이메일과_코드타입_조합으로_코드를_조회하면_성공한다() {
        // Given
        EmailCode emailCode = createEmailCode("test@email.com", EmailCodeType.EMAIL_VERIFICATION_CODE);
        EmailCode savedEmailCode = fakeEmailCodeRepository.save(emailCode);

        // When
        EmailCode foundCode = emailCodeReader.getByEmailAndType("test@email.com", EmailCodeType.EMAIL_VERIFICATION_CODE);

        // Then
        assertThat(foundCode.getId()).isEqualTo(savedEmailCode.getId());
        assertThat(foundCode.getEmail()).isEqualTo(savedEmailCode.getEmail());
        assertThat(foundCode.getCode()).isEqualTo(savedEmailCode.getCode());
        assertThat(foundCode.getType()).isEqualTo(savedEmailCode.getType());
        assertThat(foundCode.getStatus()).isEqualTo(savedEmailCode.getStatus());
        assertThat(foundCode.getCreatedDate()).isEqualTo(savedEmailCode.getCreatedDate());
        assertThat(foundCode.getExpiresDate()).isEqualTo(savedEmailCode.getExpiresDate());
    }

    @TestFactory
    Collection<DynamicTest> 존재하지않는_이메일과_코드타입_조합으로_코드를_조회하면_실패한다() {
        // Given
        EmailCode emailCode = createEmailCode("test@email.com", EmailCodeType.EMAIL_VERIFICATION_CODE);
        fakeEmailCodeRepository.save(emailCode);

        return List.of(
                DynamicTest.dynamicTest("이메일은 같지만 코드타입이 다르다면 실패한다", () -> {
                    // When & Then
                    assertThatThrownBy(() -> emailCodeReader.getByEmailAndType("test@email.com", EmailCodeType.PASSWORD_RESET_CODE))
                            .isInstanceOf(ResourceNotFoundException.class);
                }),
                DynamicTest.dynamicTest("이메일은 다르고 코드타입이 같다면 실패한다", () -> {
                    // When & Then
                    assertThatThrownBy(() -> emailCodeReader.getByEmailAndType("unknown@email.com", EmailCodeType.EMAIL_VERIFICATION_CODE))
                            .isInstanceOf(ResourceNotFoundException.class);
                }),
                DynamicTest.dynamicTest("이메일은 다르고 코드타입이 다르다면 실패한다", () -> {
                    // When & Then
                    assertThatThrownBy(() -> emailCodeReader.getByEmailAndType("unknown@email.com", EmailCodeType.PASSWORD_RESET_CODE))
                            .isInstanceOf(ResourceNotFoundException.class);
                })
        );
    }
}