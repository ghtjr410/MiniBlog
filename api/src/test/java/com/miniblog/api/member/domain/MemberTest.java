package com.miniblog.api.member.domain;

import com.miniblog.api.member.application.dto.SignupData;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MemberTest {

    private final SignupData data = SignupData.of("user123", "password123", "test@example.com", "nick1");
    private final String encodedPassword = "encodedPassword";
    private final RoleType role = RoleType.ROLE_USER;
    private final LocalDateTime now = LocalDateTime.now();

    @Test
    void 회원을_정상적으로_생성한다() {
        // Given

        // When
        Member member = Member.create(data, encodedPassword, role, now);

        // Then
        assertThat(member.getId()).isNull();
        assertThat(member.getUsername()).isEqualTo(data.username());
        assertThat(member.getPassword()).isEqualTo(encodedPassword);
        assertThat(member.getEmail()).isEqualTo(data.email());
        assertThat(member.getNickname()).isEqualTo(data.nickname());
        assertThat(member.getRole()).isEqualTo(role);
        assertThat(member.getCreatedDate()).isEqualTo(now);
    }

    @Test
    void 마지막_로그인_날짜를_정상적으로_기록한다() {
        // Given
        LocalDateTime oldLoginTime = now.minusMinutes(5);
        Member member = Member.create(data, encodedPassword, role, oldLoginTime);

        // When
        member.recordLastLoginDate(now);

        // Then
        assertThat(member.getLastLoginDate()).isEqualTo(now);
        assertThat(member.getLastLoginDate()).isAfter(oldLoginTime);
    }

    @Test
    void 닉네임을_변경하면_새로운_닉네임이_적용된다() {
        // Given
        Member member = Member.create(data, encodedPassword, role, now);
        String newNickname = "newNick";

        // When
        member.changeNickname(newNickname);

        // Then
        assertThat(member.getNickname()).isEqualTo(newNickname);
    }

    @Test
    void 비밀번호를_변경하면_새로운_비밀번호가_적용된다() {
        // Given
        Member member = Member.create(data, encodedPassword, role, now);
        String newEncodedPassword = "newEncodedPassword";

        // When
        member.changePassword(newEncodedPassword);

        // Then
        assertThat(member.getPassword()).isEqualTo(newEncodedPassword);
    }
}