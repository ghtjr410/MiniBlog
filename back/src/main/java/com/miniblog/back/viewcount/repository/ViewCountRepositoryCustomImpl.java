package com.miniblog.back.viewcount.repository;

import com.miniblog.back.viewcount.model.QViewCount;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ViewCountRepositoryCustomImpl implements ViewCountRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QViewCount viewCount = QViewCount.viewCount;

    @Override
    public void deleteByPostIds(List<Long> postIds) {
        queryFactory.delete(viewCount)
                .where(viewCount.post.id.in(postIds))
                .execute();
    }
}
