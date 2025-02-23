package com.miniblog.api.member.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.member.api.dto.request.EmailVerificationIssueRequest;
import com.miniblog.api.member.api.dto.request.EmailVerificationVerifyRequest;
import com.miniblog.api.member.application.business.EmailVerificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/email-verification")
@RequiredArgsConstructor
public class EmailVerificationController {
    private final EmailVerificationService emailVerificationManager;


    @PostMapping("/issue")
    public ResponseEntity<ApiResponse<?>> issueCode(
            @Valid @RequestBody EmailVerificationIssueRequest request
    ) {
        emailVerificationManager.issueCode(request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyCode(
            @Valid @RequestBody EmailVerificationVerifyRequest request
    ) {
        emailVerificationManager.verifyCode(request.email(), request.code());
        return ResponseEntity.noContent().build();
    }
}
