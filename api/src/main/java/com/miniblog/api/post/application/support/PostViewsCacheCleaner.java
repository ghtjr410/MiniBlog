package com.miniblog.api.post.application.support;

import com.miniblog.api.post.application.port.PostViewsCacheStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostViewsCacheCleaner {

    private final PostViewsCacheStore postViewsCacheStore;

    /**
     * 처리 완료된 조회수 데이터를 캐시에서 제거
     */
    public void clearProcessedViewCounts(Set<Long> postIds) {
        Set<String> cacheKeys = postIds.stream()
                .map(id -> "post:" + id)
                .collect(Collectors.toSet());

        postViewsCacheStore.deleteViewCounts(cacheKeys);
        log.info("[PostViewsCacheCleaner] 처리된 조회수 캐시 삭제 완료");
    }
}