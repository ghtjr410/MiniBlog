package com.miniblog.back.post.repository;

import com.miniblog.back.post.model.QPostAggregate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PostAggregateRepositoryCustomImpl implements PostAggregateRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QPostAggregate postAggregate = QPostAggregate.postAggregate;

    @Override
    public void deleteByPostIds(List<Long> postIds) {
        queryFactory.delete(postAggregate)
                .where(postAggregate.post.id.in(postIds))
                .execute();
    }
}
