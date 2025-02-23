package com.miniblog.api.auth.application.port;

import com.miniblog.api.auth.application.dto.JwtTokensData;
import com.miniblog.api.member.domain.Member;

import java.util.List;

public interface TokenProvider {
    JwtTokensData generateTokens(Member member, List<String> roles);
    String generateAccessToken(Member member, List<String> roles);
    String generateRefreshToken(Member member);
}
