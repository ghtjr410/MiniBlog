package com.miniblog.api.member.api;

import com.miniblog.api.member.application.business.MemberDuplicateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/members/duplicate-check")
@RequiredArgsConstructor
public class MemberDuplicateController {
    private final MemberDuplicateService memberDuplicateService;

    @GetMapping("/username/{username}")
    public ResponseEntity<Void> isUsernameAvailable(
            @PathVariable String username
    ) {
        memberDuplicateService.ensureNonDuplicateUsername(username);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<Void> isNicknameAvailable(
            @PathVariable String nickname
    ) {
        memberDuplicateService.ensureNonDuplicateNickname(nickname);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Void> isEmailAvailable(
            @PathVariable String email
    ) {
        memberDuplicateService.ensureNonDuplicateEmail(email);
        return ResponseEntity.noContent().build();
    }
}
