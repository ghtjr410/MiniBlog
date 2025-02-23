package com.miniblog.api.member.api;

import com.miniblog.api.common.api.ApiResponse;
import com.miniblog.api.common.api.MemberId;
import com.miniblog.api.member.api.dto.request.NicknameChangeRequest;
import com.miniblog.api.member.api.dto.request.PasswordChangeRequest;
import com.miniblog.api.member.api.dto.response.NicknameChangeResponse;
import com.miniblog.api.member.application.business.MemberProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/profile")
@RequiredArgsConstructor
public class MemberProfileController {

    private final MemberProfileService memberUpdateService;

    @PutMapping("/nickname")
    public ResponseEntity<ApiResponse<?>> changeNickname(
            @MemberId Long memberId,
            @Valid @RequestBody NicknameChangeRequest request
    ) {
        String changedNickname = memberUpdateService.changeNickname(memberId, request.nickname());
        return ResponseEntity.ok(ApiResponse.success(NicknameChangeResponse.of(changedNickname)));
    }

    @PutMapping("/password")
    public ResponseEntity<ApiResponse<?>> changePassword(
            @MemberId Long memberId,
            @Valid @RequestBody PasswordChangeRequest request
    ) {
        memberUpdateService.changePassword(memberId, request.password());
        return ResponseEntity.noContent().build();
    }
}
