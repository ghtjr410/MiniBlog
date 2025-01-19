package com.miniblog.back.auth.repository;

import com.miniblog.back.auth.dto.internal.RefreshTokenDTO;
import com.miniblog.back.auth.model.QToken;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class TokenRepositoryCustomImpl implements TokenRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    private final QToken token = QToken.token;
    @Override

    public List<RefreshTokenDTO> findTokensByMemberId(Long memberId) {
        return queryFactory.
                select(Projections.constructor(
                        RefreshTokenDTO.class,
                        token.refreshToken
                ))
                .from(token)
                .where(token.member.id.eq(memberId))
                .fetch();
    }
}
