package com.miniblog.back.member.repository;

import com.miniblog.back.member.model.QEmailVerification;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class EmailVerificationRepositoryCustomImpl implements EmailVerificationRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QEmailVerification emailVerification = QEmailVerification.emailVerification;

    @Override
    public boolean existsValidVerification(String username, String email, String code) {
        return queryFactory
                .selectOne()
                .from(emailVerification)
                .where(
                        emailVerification.username.eq(username),
                        emailVerification.email.eq(email),
                        emailVerification.code.eq(code),
                        emailVerification.expiresDate.after(LocalDateTime.now())
                )
                .fetchOne() != null;
    }

    @Override
    public void deleteVerification(String username, String email, String code) {
        queryFactory.delete(emailVerification)
                .where(
                        emailVerification.username.eq(username),
                        emailVerification.email.eq(email),
                        emailVerification.code.eq(code)
                )
                .execute();
    }
}
