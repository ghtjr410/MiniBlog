package com.miniblog.api.member.application.support;

import com.miniblog.api.fake.common.FakeClockHolder;
import com.miniblog.api.fake.member.FakeMemberRepository;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.member.domain.RoleType;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberRecorderTest {

    private FakeClockHolder fakeClockHolder;
    private FakeMemberRepository fakeMemberRepository;
    private MemberRecorder memberRecorder;
    private final LocalDateTime fixedDateTime = LocalDateTime.of(2024, 1, 1, 12, 0);

    @BeforeEach
    void setUp() {

        fakeClockHolder = new FakeClockHolder(fixedDateTime);
        fakeMemberRepository = new FakeMemberRepository();
        memberRecorder = new MemberRecorder(fakeClockHolder, fakeMemberRepository);
    }

    @Test
    void 마지막_로그인_시간을_정상적으로_업데이트한다() {
        // Given
        LocalDateTime lastLoginDate = LocalDateTime.of(2024, 1, 1, 0, 0);

        Member member = Member.builder()
                .lastLoginDate(lastLoginDate)
                .build();

        Member savedMember = fakeMemberRepository.save(member);
        // When
        memberRecorder.recordLastLogin(savedMember);

        // Then
        Member updatedMember = fakeMemberRepository.findById(savedMember.getId()).orElseThrow();

        assertThat(updatedMember.getLastLoginDate()).isNotEqualTo(lastLoginDate);
        assertThat(updatedMember.getLastLoginDate()).isAfter(lastLoginDate);
        assertThat(updatedMember.getLastLoginDate()).isEqualTo(fixedDateTime);
    }

    @Test
    void 마지막_활동_시간을_정상적으로_업데이트한다() {
        // Given
        LocalDateTime lastActivityDate = LocalDateTime.of(2024, 1, 1, 0, 0);

        Member member = Member.builder()
                .lastActivityDate(lastActivityDate)
                .build();

        Member savedMember = fakeMemberRepository.save(member);


        // When
        memberRecorder.recordLastActivity(savedMember.getId());

        // Then
        Member updatedMember = fakeMemberRepository.findById(savedMember.getId()).orElseThrow();

        assertThat(updatedMember.getLastActivityDate()).isNotEqualTo(lastActivityDate);
        assertThat(updatedMember.getLastActivityDate()).isAfter(lastActivityDate);
        assertThat(updatedMember.getLastActivityDate()).isEqualTo(fixedDateTime);
    }
}