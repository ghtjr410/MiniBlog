package com.miniblog.back.auth.repository;

import com.miniblog.back.auth.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long>, TokenRepositoryCustom {
    Optional<Token> findByRefreshToken(String refreshToken);
    void deleteByMemberId(Long memberId);
}
