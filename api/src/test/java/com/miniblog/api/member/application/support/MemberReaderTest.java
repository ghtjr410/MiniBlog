package com.miniblog.api.member.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.fake.member.FakeMemberRepository;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.member.domain.RoleType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberReaderTest {

    private FakeMemberRepository fakeMemberRepository;
    private MemberReader memberReader;

    @BeforeEach
    void setUp() {
        fakeMemberRepository = new FakeMemberRepository();
        memberReader = new MemberReader(fakeMemberRepository);
    }

    private Member createMember(String username, String email, String nickname) {
        LocalDateTime fixedDateTime = LocalDateTime.of(2025, 1, 1, 12, 0, 0);

        return Member.builder()
                .username(username)
                .password("encodedPassword")
                .nickname(nickname)
                .email(email)
                .role(RoleType.ROLE_USER)
                .createdDate(fixedDateTime)
                .lastLoginDate(fixedDateTime)
                .lastActivityDate(fixedDateTime)
                .build();
    }

    @Test
    void 회원_ID로_정상적으로_조회한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        Member savedMember = fakeMemberRepository.save(member);

        // When
        Member foundMember = memberReader.getById(savedMember.getId());

        // Then
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(foundMember.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(foundMember.getRole()).isEqualTo(savedMember.getRole());
        assertThat(foundMember.getCreatedDate()).isEqualTo(savedMember.getCreatedDate());
        assertThat(foundMember.getLastLoginDate()).isEqualTo(savedMember.getLastLoginDate());
        assertThat(foundMember.getLastActivityDate()).isEqualTo(savedMember.getLastActivityDate());
    }

    @Test
    void 존재하지_않는_회원_ID를_조회하면_예외가_발생한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When & Then
        assertThatThrownBy(() -> memberReader.getById(1000L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 사용자명으로_정상적으로_조회한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        Member savedMember = fakeMemberRepository.save(member);

        // When
        Member foundMember = memberReader.getByUsername(savedMember.getUsername());

        // Then
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(foundMember.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(foundMember.getRole()).isEqualTo(savedMember.getRole());
        assertThat(foundMember.getCreatedDate()).isEqualTo(savedMember.getCreatedDate());
        assertThat(foundMember.getLastLoginDate()).isEqualTo(savedMember.getLastLoginDate());
        assertThat(foundMember.getLastActivityDate()).isEqualTo(savedMember.getLastActivityDate());;
    }

    @Test
    void 존재하지_않는_사용자명을_조회하면_예외가_발생한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When & Then
        assertThatThrownBy(() -> memberReader.getByUsername("unknownUser"))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void 이메일로_정상적으로_조회한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        Member savedMember = fakeMemberRepository.save(member);

        // When
        Member foundMember = memberReader.getByEmail(savedMember.getEmail());

        // Then
        assertThat(foundMember.getId()).isEqualTo(savedMember.getId());
        assertThat(foundMember.getUsername()).isEqualTo(savedMember.getUsername());
        assertThat(foundMember.getPassword()).isEqualTo(savedMember.getPassword());
        assertThat(foundMember.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(foundMember.getNickname()).isEqualTo(savedMember.getNickname());
        assertThat(foundMember.getRole()).isEqualTo(savedMember.getRole());
        assertThat(foundMember.getCreatedDate()).isEqualTo(savedMember.getCreatedDate());
        assertThat(foundMember.getLastLoginDate()).isEqualTo(savedMember.getLastLoginDate());
        assertThat(foundMember.getLastActivityDate()).isEqualTo(savedMember.getLastActivityDate());;
    }

    @Test
    void 존재하지_않는_이메일을_조회하면_예외가_발생한다() {
        // Given
        Member member = createMember("testUser", "testNick", "test@email.com");
        fakeMemberRepository.save(member);

        // When & Then
        assertThatThrownBy(() -> memberReader.getByEmail("unknown@test.com"))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}