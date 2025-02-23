package com.miniblog.api.member.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.member.api.dto.request.SignupRequest;
import com.miniblog.api.member.application.dto.SignupData;
import com.miniblog.api.member.application.business.SignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/signup")
@RequiredArgsConstructor
public class SignupController {
    private final SignupService signupService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(
            @Valid @RequestBody SignupRequest request
    ) {
        SignupData data = SignupData.of(request.username(), request.password(), request.email(), request.nickname());
        signupService.signup(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
    }
}
