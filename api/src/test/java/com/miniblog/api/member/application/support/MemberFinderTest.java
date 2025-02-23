package com.miniblog.api.member.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.fake.member.FakeMemberRepository;
import com.miniblog.api.member.domain.Member;
import org.junit.jupiter.api.*;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberFinderTest {

    private FakeMemberRepository fakeMemberRepository;
    private MemberFinder memberFinder;

    @BeforeEach
    void setUp() {
        fakeMemberRepository = new FakeMemberRepository();
        memberFinder = new MemberFinder(fakeMemberRepository);
    }

    private Member createMember(String username, String nickname, String email) {
        return Member.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .build();
    }

    @Test
    void 존재하는_회원_ID를_확인하면_예외가_발생하지_않는다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        Member savedMember = fakeMemberRepository.save(member);

        // When & Then
        assertThatCode(() -> memberFinder.ensureExistsById(savedMember.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 존재하지_않는_회원_ID를_확인하면_예외가_발생한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When & Then
        assertThatThrownBy(() -> memberFinder.ensureExistsById(1000L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 존재하는_사용자명인지_확인하면_true를_반환한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When
        boolean exists = memberFinder.existsByUsername("testUser");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void 존재하지_않는_사용자명이면_false를_반환한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When
        boolean exists = memberFinder.existsByUsername("unknownUser");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void 존재하는_닉네임인지_확인하면_true를_반환한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When
        boolean exists = memberFinder.existsByNickname("testNick");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void 존재하지_않는_닉네임인지_확인하면_false를_반환한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When
        boolean exists = memberFinder.existsByNickname("unknownNick");

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void 존재하는_이메일인지_확인하면_true를_반환한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When
        boolean exists = memberFinder.existsByEmail("test@email.com");

        // Then
        assertThat(exists).isTrue();
    }

    @Test
    void 존재하지_않는_이메일인지_확인하면_false를_반환한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When
        boolean exists = memberFinder.existsByEmail("unknown@email.com");

        // Then
        assertThat(exists).isFalse();
    }
}