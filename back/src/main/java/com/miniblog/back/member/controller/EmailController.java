package com.miniblog.back.member.controller;

import com.miniblog.back.member.dto.request.EmailVerificationRequestDTO;
import com.miniblog.back.member.dto.request.VerifyEmailCodeRequestDTO;
import com.miniblog.back.member.dto.response.VerificationResponseDTO;
import com.miniblog.back.member.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/emails")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/verification")
    public ResponseEntity<Void> sendEmailVerification(
            @Valid @RequestBody EmailVerificationRequestDTO requestDTO
    ) {
        emailService.sendEmailVerification(requestDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verification/verify")
    public ResponseEntity<VerificationResponseDTO> verifyEmailCode(
            @Valid @RequestBody VerifyEmailCodeRequestDTO requestDTO
    ) {
        VerificationResponseDTO responseDTO = emailService.verifyEmailCode(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }
}
