package com.miniblog.back.viewcount.repository;

import java.util.List;

public interface ViewCountRepositoryCustom {
    void deleteByPostIds(List<Long> postIds);
}
