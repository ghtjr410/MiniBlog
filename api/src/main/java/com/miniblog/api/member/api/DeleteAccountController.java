package com.miniblog.api.member.api;

import com.miniblog.api.common.api.MemberId;
import com.miniblog.api.member.api.dto.request.DeleteAccountRequest;
import com.miniblog.api.member.application.business.DeleteAccountService;
import com.miniblog.api.member.application.dto.DeleteAccountData;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class DeleteAccountController {

    private final DeleteAccountService deleteAccountService;

    @PostMapping("/delete-account")
    public ResponseEntity<Void> deleteAccount(
            @CookieValue(name = "refresh_token") String refreshToken,
            @MemberId Long memberId,
            @RequestBody DeleteAccountRequest request
    ) {
        DeleteAccountData data = DeleteAccountData.of(request, memberId, refreshToken);

        deleteAccountService.deleteAccount(data);

        return ResponseEntity.noContent().build();
    }

}
