package com.miniblog.api.auth.application.business;

import com.miniblog.api.auth.application.support.TokenFinder;
import com.miniblog.api.auth.application.support.TokenRevoker;
import com.miniblog.api.auth.application.support.TokenValidator;
import com.miniblog.api.auth.domain.RefreshToken;
import com.miniblog.api.member.application.support.MemberFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final TokenFinder tokenFinder;
    private final TokenValidator tokenValidator;
    private final MemberFinder memberFinder;
    private final TokenRevoker tokenRevoker;

    /**
     * 사용자 로그아웃 및 토큰 무효화
     */
    @Transactional
    public void logout(String receivedToken, String deviceInfo) {
        RefreshToken refreshToken = tokenFinder.findByToken(receivedToken);
        tokenValidator.validate(refreshToken, receivedToken, deviceInfo);

        memberFinder.ensureExistsById(refreshToken.getMember().getId());

        tokenRevoker.revoke(refreshToken.getToken(), refreshToken.getExpiresDate());
    }
}
