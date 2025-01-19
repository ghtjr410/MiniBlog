package com.miniblog.back.member.controller;

import com.miniblog.back.member.dto.request.RegisterRequestDTO;
import com.miniblog.back.member.dto.response.AvailabilityResponseDTO;
import com.miniblog.back.member.dto.response.RegisterResponseDTO;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/username-available")
    public ResponseEntity<AvailabilityResponseDTO> isUsernameAvailable(
            @RequestParam String username
    ) {
        AvailabilityResponseDTO responseDTO = memberService.isUsernameAvailable(username);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/nickname-available")
    public ResponseEntity<AvailabilityResponseDTO> isNicknameAvailable(
            @RequestParam String nickname
    ) {
        AvailabilityResponseDTO responseDTO = memberService.isNicknameAvailable(nickname);
        return ResponseEntity.ok(responseDTO);
    }


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerMember(
            @RequestBody RegisterRequestDTO requestDTO
    ) {
        RegisterResponseDTO responseDTO = memberService.registerMember(requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMember(
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        memberService.deleteMember(authorizationHeader);
        return ResponseEntity.noContent().build();
    }
}
