package com.miniblog.api.member.infrastructure.persistence;

import com.miniblog.api.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    @Modifying
    @Query("UPDATE Member m SET m.lastActivityDate = :now WHERE m.id = :memberId")
    void updateLastActivity(@Param("memberId") long memberId, @Param("now") LocalDateTime now);


    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);

    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
