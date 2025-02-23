package com.miniblog.api.member.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PasswordResetCodeIssueRequest(
        @NotBlank(message = "사용자명은 필수 입력 항목입니다.")
        String username
) {
}
