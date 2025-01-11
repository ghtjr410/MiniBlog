package com.miniblog.back.auth.repository;

import com.miniblog.back.auth.model.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {
    boolean existsByRefreshToken(String refreshToken);
}
