package com.miniblog.back.post.repository;

import java.util.List;

public interface PostAggregateRepositoryCustom {
    void deleteByPostIds(List<Long> postIds);
}
