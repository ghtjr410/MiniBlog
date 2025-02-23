package com.miniblog.api.auth.application.port;

import com.miniblog.api.auth.application.dto.MemberInfoData;
import com.miniblog.api.auth.application.dto.RefreshTokenClaimsData;

import java.time.LocalDateTime;

public interface TokenParser {
    MemberInfoData extractMemberInfo(String token);
    RefreshTokenClaimsData extractRefreshTokenClaims(String token);
}
