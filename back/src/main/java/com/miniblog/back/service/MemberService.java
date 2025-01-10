package com.miniblog.back.service;

import com.miniblog.back.dto.member.RegisterRequest;
import com.miniblog.back.mapper.MemberMapper;
import com.miniblog.back.model.Member;
import com.miniblog.back.repository.MemberRepository;
import com.miniblog.back.security.PrincipalDetails;
import com.miniblog.back.validator.MemberValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;
    private final MemberMapper memberMapper;

    public Member registerMember(RegisterRequest request) {
        // 유효성 검사
        memberValidator.validateForRegistration(request);

        // Member 객체 생성
        Member newMember = memberMapper.create(request);

        // 데이터 저장
        return memberRepository.save(newMember);
    }

    @Override
    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new PrincipalDetails(member);
    }

}
