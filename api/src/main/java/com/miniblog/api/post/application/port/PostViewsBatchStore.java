package com.miniblog.api.post.application.port;

import java.util.List;
import java.util.Map;

public interface PostViewsBatchStore {

    /**
     * 조회수 데이터를 임시 저장할 테이블을 생성
     */
    void prepareTemporaryStorage();

    /**
     * 조회수 데이터를 임시 테이블에 일괄 삽입
     */
    void storeBatchViewCounts(List<Map.Entry<Long, Integer>> viewCounts);

    /**
     * 임시 저장된 조회수를 실제 데이터베이스 테이블과 병합하여 업데이트
     */
    void mergeViewCountsToDatabase();

    /**
     * 임시 저장소의 모든 데이터를 삭제 (초기화)
     */
    void resetTemporaryStorage();

    /**
     * 임시 저장소(테이블)를 삭제
     */
    void removeTemporaryStorage();
}
