package com.miniblog.back.auth.repository;

import com.miniblog.back.auth.model.BlacklistToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken, Long> {


    @Query(value = "SELECT EXISTS (SELECT 1 FROM blacklist_token WHERE refresh_token = :token)", nativeQuery = true)
    Long existsByRefreshToken(@Param("token") String refreshToken);
}
