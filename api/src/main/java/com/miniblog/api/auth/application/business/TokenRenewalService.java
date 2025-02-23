package com.miniblog.api.auth.application.business;

import com.miniblog.api.auth.application.dto.JwtTokensData;
import com.miniblog.api.auth.application.support.TokenFinder;
import com.miniblog.api.auth.application.support.TokenIssuer;
import com.miniblog.api.auth.application.support.TokenRevoker;
import com.miniblog.api.auth.application.support.TokenValidator;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenRenewalService {

    private final TokenFinder tokenFinder;
    private final TokenValidator tokenValidator;
    private final TokenRevoker tokenRevoker;
    private final MemberReader memberReader;
    private final TokenIssuer tokenIssuer;

    /**
     * 토큰 갱신 및 새 JWT 발급
     */
    @Transactional
    public JwtTokensData renew(String receivedToken, String deviceInfo) {
        RefreshToken refreshToken = tokenFinder.findByToken(receivedToken);
        tokenValidator.validate(refreshToken, receivedToken, deviceInfo);

        tokenRevoker.revoke(refreshToken.getToken(), refreshToken.getExpiresDate());

        Member member = memberReader.getById(refreshToken.getMember().getId());
        List<String> roles = List.of(member.getRole().toString());

        return tokenIssuer.issueTokens(member, roles, deviceInfo);
    }
}
