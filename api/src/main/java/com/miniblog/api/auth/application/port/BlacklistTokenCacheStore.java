package com.miniblog.api.auth.application.port;

import com.miniblog.api.auth.domain.BlacklistToken;

import java.util.List;

public interface BlacklistTokenCacheStore {
    void save(BlacklistToken blacklistToken);
    void saveAll(List<BlacklistToken> blacklistTokens);
    boolean existsByToken(String token);
}
