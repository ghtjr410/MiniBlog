package com.miniblog.api.fake.auth;

import com.miniblog.api.auth.application.port.BlacklistTokenCacheStore;
import com.miniblog.api.auth.domain.BlacklistToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FakeBlacklistTokenCacheStore implements BlacklistTokenCacheStore {

    private final Set<String> blacklistedTokens = new HashSet<>();

    @Override
    public void save(BlacklistToken blacklistToken) {
        blacklistedTokens.add(blacklistToken.getToken());
    }

    @Override
    public void saveAll(List<BlacklistToken> blacklistTokens) {
        for (BlacklistToken token : blacklistTokens) {
            blacklistedTokens.add(token.getToken());
        }
    }

    @Override
    public boolean existsByToken(String token) {
        return blacklistedTokens.contains(token);
    }
}
