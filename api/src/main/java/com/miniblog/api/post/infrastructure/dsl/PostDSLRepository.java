package com.miniblog.api.post.infrastructure.dsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.miniblog.api.post.domain.QPost.*;

@Repository
@RequiredArgsConstructor
public class PostDSLRepository {

    private final JPAQueryFactory query;

    public List<Long> findIdsByMemberId(Long memberId) {
        return query
                .select(post.id)
                .from(post)
                .where(post.member.id.eq(memberId))
                .fetch();
    }

}
