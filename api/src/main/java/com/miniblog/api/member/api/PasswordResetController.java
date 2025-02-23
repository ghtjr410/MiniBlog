package com.miniblog.api.member.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.member.api.dto.request.PasswordResetCodeIssueRequest;
import com.miniblog.api.member.api.dto.request.PasswordResetCodeVerifyRequest;
import com.miniblog.api.member.application.business.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordResetService passwordResetManager;

    @PostMapping("/issue")
    public ResponseEntity<ApiResponse<?>> issueCode(
            @Valid @RequestBody PasswordResetCodeIssueRequest request
    ) {
        passwordResetManager.issueCode(request.username());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
    }

    @PostMapping("/verify")
    public ResponseEntity<Void> verifyCode(
            @Valid @RequestBody PasswordResetCodeVerifyRequest request
    ) {
        passwordResetManager.verifyCode(request.username(), request.code());
        return ResponseEntity.noContent().build();
    }
}
