package com.miniblog.api.like.domain;

import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.domain.Post;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LikeHistoryTest {

    private final LocalDateTime now = LocalDateTime.now();
    private final Post post = Post.builder().id(1L).build();
    private final Member member = Member.builder().id(1L).build();

    @Test
    void 좋아요를_정상적으로_추가한다() {
        // Given

        // When
        LikeHistory likeHistory = LikeHistory.create(member, post, now);

        // Then
        assertThat(likeHistory.getId()).isNull();
        assertThat(likeHistory.getCreatedDate()).isEqualTo(now);
        assertThat(likeHistory.getMember().getId()).isEqualTo(member.getId());
        assertThat(likeHistory.getPost().getId()).isEqualTo(post.getId());
    }
}