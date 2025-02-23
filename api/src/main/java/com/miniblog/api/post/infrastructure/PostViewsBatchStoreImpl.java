package com.miniblog.api.post.infrastructure;

import com.miniblog.api.post.application.port.PostViewsBatchStore;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PostViewsBatchStoreImpl implements PostViewsBatchStore {

    private final EntityManager em;

    @Override
    public void prepareTemporaryStorage() {
        em.createNativeQuery("""
                CREATE TEMPORARY TABLE IF NOT EXISTS temp_post_views (
                    post_id BIGINT PRIMARY KEY,
                    increment INT NOT NULL
                )
                """).executeUpdate();
    }

    @Override
    public void storeBatchViewCounts(List<Map.Entry<Long, Integer>> viewCounts) {
        if (viewCounts.isEmpty()) {
            return;
        }

        String insertQuery =
                "INSERT INTO temp_post_views (post_id, increment) VALUES " +
                viewCounts.stream()
                        .map(entry -> "(" + entry.getKey() + "," + entry.getValue() + ")")
                        .collect(Collectors.joining(","));

        em.createNativeQuery(insertQuery).executeUpdate();
    }

    @Override
    public void mergeViewCountsToDatabase() {
        em.createNativeQuery("""
                UPDATE post_views p
                JOIN temp_post_views t ON p.post_id = t.post_id
                SET p.view_count = p.view_count + t.increment
                """).executeUpdate();
    }

    @Override
    public void resetTemporaryStorage() {
        em.createNativeQuery("DELETE FROM temp_post_views").executeUpdate();
    }

    @Override
    public void removeTemporaryStorage() {
        em.createNativeQuery("DROP TEMPORARY TABLE IF EXISTS temp_post_views");
    }
}
