package com.miniblog.api.member.application.support;

import com.miniblog.api.fake.common.FakeClockHolder;
import com.miniblog.api.fake.member.FakeEmailCodeRepository;
import com.miniblog.api.member.domain.EmailCode;
import com.miniblog.api.member.domain.EmailCodeType;
import com.miniblog.api.member.infrastructure.code.CodeGeneratorFactory;
import com.miniblog.api.member.infrastructure.code.EmailVerificationCodeGenerator;
import com.miniblog.api.member.infrastructure.code.MemberSecurityCodeGenerator;
import com.miniblog.api.member.infrastructure.mail.MailSenderFactory;
import com.miniblog.api.member.infrastructure.mail.MailSenderType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmailCodeIssuerTest {

    @Mock
    private MailSenderFactory mailSenderFactory;

    private CodeGeneratorFactory codeGeneratorFactory;
    private FakeEmailCodeRepository fakeEmailCodeRepository;
    private FakeClockHolder fakeClockHolder;
    private EmailCodeIssuer emailCodeIssuer;

    LocalDateTime fixedDateTime = LocalDateTime.of(2024, 1, 1, 12, 0);

    @BeforeEach
    void setUp() {
        fakeClockHolder = new FakeClockHolder(LocalDateTime.of(2024, 1, 1, 12, 0));

        codeGeneratorFactory = new CodeGeneratorFactory(
                new EmailVerificationCodeGenerator(),
                new MemberSecurityCodeGenerator());

        fakeEmailCodeRepository = new FakeEmailCodeRepository();

        emailCodeIssuer = new EmailCodeIssuer(
                fakeClockHolder,
                fakeEmailCodeRepository,
                codeGeneratorFactory,
                mailSenderFactory);
    }

    @Test
    void 기존_이메일_코드가_존재하면_삭제한다() {
        // Given
        String email = "test@email.com";
        EmailCodeType codeType = EmailCodeType.EMAIL_VERIFICATION_CODE;
        String code = "1234";
        MailSenderType mailType = MailSenderType.EMAIL_VERIFICATION_MAIL;

        EmailCode oldEmailCode = EmailCode.create(email, codeType, code, fixedDateTime);
        oldEmailCode = fakeEmailCodeRepository.save(oldEmailCode);


        // When
        emailCodeIssuer.issueCode(email, codeType, mailType);

        // Then
        assertThat(fakeEmailCodeRepository.findByEmailAndType(email, codeType)).isPresent()
                .get()
                .extracting(EmailCode::getCode)
                .isNotNull()
                .isNotEqualTo(oldEmailCode.getCode());

    }

    @Test
    void 새로운_코드를_생성하여_정확하게_저장한다() {
        // Given
        String email = "test@email.com";
        EmailCodeType codeType = EmailCodeType.EMAIL_VERIFICATION_CODE;
        MailSenderType mailType = MailSenderType.EMAIL_VERIFICATION_MAIL;

        // When
        emailCodeIssuer.issueCode(email, codeType, mailType);

        // Then
        EmailCode savedCode = fakeEmailCodeRepository.findByEmailAndType(email, codeType).orElseThrow();

        assertThat(savedCode.getEmail()).isEqualTo(email);
        assertThat(savedCode.getType()).isEqualTo(codeType);
        assertThat(savedCode.getCode()).isNotBlank();
        assertThat(savedCode.getCreatedDate()).isEqualTo(fixedDateTime);
    }

    @Test
    void 이메일을_정상적으로_전송한다() {
        // Given
        String email = "test@email.com";
        EmailCodeType codeType = EmailCodeType.EMAIL_VERIFICATION_CODE;
        MailSenderType mailType = MailSenderType.EMAIL_VERIFICATION_MAIL;

        // When
        emailCodeIssuer.issueCode(email, codeType, mailType);

        // Then
        EmailCode savedCode = fakeEmailCodeRepository.findByEmailAndType(email, codeType).orElseThrow();

        verify(mailSenderFactory).sendMail(savedCode.getEmail(), savedCode.getCode(), mailType);
    }
}