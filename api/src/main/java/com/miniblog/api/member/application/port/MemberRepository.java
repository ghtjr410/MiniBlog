package com.miniblog.api.member.application.port;

import com.miniblog.api.member.domain.Member;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    void updateLastActivity(Long memberId, LocalDateTime now);

    void delete(Member member);

    Optional<Member> findById(long id);
    Optional<Member> findByUsername(String username);
    Optional<Member> findByEmail(String email);

    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
}
