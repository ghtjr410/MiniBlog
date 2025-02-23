package com.miniblog.api.post.application.support;

import com.miniblog.api.post.application.port.PostViewsCacheStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostViewsBatchSyncScheduler {

    private final PostViewsCacheStore postViewsCacheStore;
    private final PostViewsBatchMerger postViewsBatchMerger;
    private final PostViewsCacheCleaner postViewsCacheCleaner;

    private static final int BATCH_SIZE = 1000;

    /**
     * 일정 주기로 캐시된 조회수를 배치 동기화 (DB 반영)
     */
    @Transactional
    @Scheduled(fixedRate = 60000)
    public void syncPostViewCountsBatch() {
        log.info("[PostViewsBulkUpdateScheduler] 조회수 배치 동기화 시작");

        Map<Long, Integer> viewCounts = postViewsCacheStore.getAllViewCounts();

        if (viewCounts.isEmpty()) {
            log.info("[PostViewsBulkUpdateScheduler] 동기화할 조회수 없음");
            return;
        }

        // 조회수 배치 병합 수행
        postViewsBatchMerger.mergePostViewCounts(viewCounts);
        
        // 동기화된 조회수 캐시 제거
        postViewsCacheCleaner.clearProcessedViewCounts(viewCounts.keySet());
    }
}
