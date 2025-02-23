package com.miniblog.api.post.application.support;

import com.miniblog.api.post.application.port.PostViewsBatchStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostViewsBatchMerger {

    private final PostViewsBatchStore postViewsBatchStore;
    private static final int BATCH_SIZE = 1000;

    /**
     * 조회수를 배치로 병합하여 DB에 반영
     */
    @Transactional
    public void mergePostViewCounts(Map<Long, Integer> viewCounts) {
        log.info("[PostViewsBatchProcessor] 조회수 병합 시작");

        postViewsBatchStore.prepareTemporaryStorage();

        List<Map.Entry<Long, Integer>> entries = new ArrayList<>(viewCounts.entrySet());

        for (int i = 0; i < entries.size(); i += BATCH_SIZE) {
            List<Map.Entry<Long, Integer>> batch = entries.subList(i, Math.min(i + BATCH_SIZE, entries.size()));
            processBatch(batch);
        }

        postViewsBatchStore.removeTemporaryStorage();
        log.info("[PostViewsBatchProcessor] 조회수 병합 완료");
    }

    private void processBatch(List<Map.Entry<Long, Integer>> batch) {
        postViewsBatchStore.storeBatchViewCounts(batch);
        postViewsBatchStore.mergeViewCountsToDatabase();
        postViewsBatchStore.resetTemporaryStorage();
    }
}
