package com.miniblog.api.auth.application.business;

import com.miniblog.api.auth.application.dto.JwtTokensData;
import com.miniblog.api.auth.application.dto.LoginData;
import com.miniblog.api.auth.application.port.SecurityExtractor;
import com.miniblog.api.auth.application.support.TokenIssuer;
import com.miniblog.api.auth.application.util.UserPrincipal;
import com.miniblog.api.member.application.support.MemberRecorder;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final MemberRecorder memberRecorder;
    private final SecurityExtractor securityExtractor;
    private final TokenIssuer tokenIssuer;

    /**
     * 사용자 로그인 및 JWT 토큰 발급
     */
    @Transactional
    public JwtTokensData login(LoginData data) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(data.username(), data.password())
        );

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        Member member = principal.getMember();
        List<String> roles = securityExtractor.extractRoles(principal.getAuthorities());

        JwtTokensData tokens = tokenIssuer.issueTokens(member, roles, data.deviceInfo());

        memberRecorder.recordLastLogin(member);

        return tokens;
    }
}
