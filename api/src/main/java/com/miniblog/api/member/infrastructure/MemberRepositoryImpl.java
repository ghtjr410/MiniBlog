package com.miniblog.api.member.infrastructure;

import com.miniblog.api.member.application.port.MemberRepository;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.member.infrastructure.persistence.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
    private final MemberJpaRepository memberJpaRepository;

    // -- Command --
    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public void updateLastActivity(Long memberId, LocalDateTime now) {
        memberJpaRepository.updateLastActivity(memberId, now);
    }

    @Override
    public void delete(Member member) {
        memberJpaRepository.delete(member);
    }

    // -- Query --

    @Override
    public Optional<Member> findById(long id) {
        return memberJpaRepository.findById(id);
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        return memberJpaRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return memberJpaRepository.existsById(id);
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberJpaRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    @Override
    public boolean existsByEmail(String email) {
        return memberJpaRepository.existsByEmail(email);
    }
}
