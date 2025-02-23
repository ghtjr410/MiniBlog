package com.miniblog.api.member.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.member.api.dto.request.UsernameRecoverIssueRequest;
import com.miniblog.api.member.api.dto.request.UsernameRecoverVerifyRequest;
import com.miniblog.api.member.api.dto.response.UsernameResponse;
import com.miniblog.api.member.application.business.UsernameRecoverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/username-recover")
@RequiredArgsConstructor
public class UsernameRecoverController {

    private final UsernameRecoverService usernameRecoverService;

    @PostMapping("/issue")
    public ResponseEntity<ApiResponse<?>> issueCode(
            @Valid @RequestBody UsernameRecoverIssueRequest request
    ) {
        usernameRecoverService.issueCode(request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success());
    }

    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<UsernameResponse>> verifyCode(
            @Valid @RequestBody UsernameRecoverVerifyRequest request
    ) {
        String username = usernameRecoverService.verifyCode(request.email(), request.code());
        UsernameResponse response = UsernameResponse.of(username);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
