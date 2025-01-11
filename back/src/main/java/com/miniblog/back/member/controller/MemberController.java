package com.miniblog.back.member.controller;

import com.miniblog.back.member.dto.request.RegisterRequest;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원가입 API
     * POST /api/members/signup
     */
    @PostMapping("/register")
    public Member registerMember(@RequestBody RegisterRequest request) {
        return memberService.registerMember(request);
    }
}
