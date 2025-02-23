package com.miniblog.api.auth.application.port;

import com.miniblog.api.auth.application.dto.MemberInfoData;

public interface SecurityContextManager {
    void setAuthentication(MemberInfoData memberInfo);
}
