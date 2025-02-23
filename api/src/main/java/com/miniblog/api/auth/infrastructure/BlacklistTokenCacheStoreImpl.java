package com.miniblog.api.auth.infrastructure;

import com.miniblog.api.auth.application.port.BlacklistTokenCacheStore;
import com.miniblog.api.auth.domain.BlacklistToken;
import com.miniblog.api.auth.infrastructure.redis.BlacklistTokenRedisStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BlacklistTokenCacheStoreImpl implements BlacklistTokenCacheStore {

    private final BlacklistTokenRedisStore blacklistTokenStore;

    @Override
    public void save(BlacklistToken blacklistToken) {
        blacklistTokenStore.save(blacklistToken);
    }

    @Override
    public void saveAll(List<BlacklistToken> blacklistTokens) {
        blacklistTokenStore.saveAll(blacklistTokens);
    }

    @Override
    public boolean existsByToken(String token) {
        return blacklistTokenStore.existsByToken(token);
    }
}
