package com.miniblog.api.auth.infrastructure.security;

import com.miniblog.api.auth.application.dto.MemberInfoData;
import com.miniblog.api.auth.application.port.SecurityContextManager;
import com.miniblog.api.auth.application.util.UserPrincipal;
import com.miniblog.api.member.domain.Member;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecurityContextManagerImpl implements SecurityContextManager {

    /**
     * 컨텍스트에 인증 정보 설정
     */
    @Override
    public void setAuthentication(MemberInfoData memberInfo) {
        List<GrantedAuthority> authorities = memberInfo.roles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        Member member = Member.builder()
                .id(memberInfo.id())
                .username(memberInfo.username())
                .nickname(memberInfo.nickname())
                .email(memberInfo.email())
                .build();

        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(
                UserPrincipal.of(member), null, authorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
