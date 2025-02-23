package com.miniblog.api.auth.infrastructure.persistence;

import com.miniblog.api.auth.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {
    void deleteByToken(String token);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.expiresDate < :now")
    void deleteExpiredTokens(@Param("now")LocalDateTime now);

    @Modifying
    @Query("DELETE FROM RefreshToken r WHERE r.member.id = :memberId")
    void deleteAllByMemberId(@Param("memberId") Long memberId);

    Optional<RefreshToken> findByToken(String token);
    List<RefreshToken> findAllByMemberId(Long memberId);
}
