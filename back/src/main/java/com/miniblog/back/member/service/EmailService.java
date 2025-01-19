package com.miniblog.back.member.service;

import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.member.dto.request.EmailVerificationRequestDTO;
import com.miniblog.back.member.dto.request.VerifyEmailCodeRequestDTO;
import com.miniblog.back.member.dto.response.VerificationResponseDTO;
import com.miniblog.back.member.email.EmailCode;
import com.miniblog.back.member.email.EmailProvider;
import com.miniblog.back.member.mapper.EmailVerificationMapper;
import com.miniblog.back.member.model.EmailVerification;
import com.miniblog.back.member.repository.EmailVerificationRepository;
import com.miniblog.back.member.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailVerificationMapper emailVerificationMapper;
    private final EmailProvider emailProvider;
    private final MemberValidator memberValidator;

    @Transactional
    public void sendEmailVerification(EmailVerificationRequestDTO requestDTO) {
        memberValidator.validateEmail(requestDTO.email());

        String code = generatedAndSendCode(requestDTO);

        emailVerificationRepository.findByUsernameAndEmail(requestDTO.username(), requestDTO.email())
                .ifPresentOrElse(
                        existing -> updateEmailVerification(existing, code),
                        () -> createEmailVerification(requestDTO, code)
                );
    }

    private String generatedAndSendCode(EmailVerificationRequestDTO requestDTO) {
        String code = EmailCode.getCode();
        emailProvider.send(requestDTO, code);
        return code;
    }

    private void updateEmailVerification(EmailVerification existing, String code) {
        emailVerificationMapper.update(existing, code);
        emailVerificationRepository.save(existing);
    }

    private void createEmailVerification(EmailVerificationRequestDTO requestDTO, String code) {
        EmailVerification emailVerification = emailVerificationMapper.create(requestDTO, code);
        emailVerificationRepository.save(emailVerification);
    }

    @Transactional(readOnly = true)
    public VerificationResponseDTO verifyEmailCode(VerifyEmailCodeRequestDTO verifyEmailCodeRequestDTO) {
        validateEmailVerification(
                verifyEmailCodeRequestDTO.username(),
                verifyEmailCodeRequestDTO.email(),
                verifyEmailCodeRequestDTO.code());

        return VerificationResponseDTO.of(true);
    }


    public void validateEmailVerification(String username, String email, String code) {
        boolean exists = emailVerificationRepository.existsValidVerification(username, email, code);
        if (!exists) {
            throw new NotFoundException("인증 정보가 존재하지 않습니다.");
        }
    }
}
