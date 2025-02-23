package com.miniblog.api.post.domain;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostCountsTest {

    @Test
    void 게시글_통계를_정상적으로_생성한다() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Post post = Post.builder()
                .id(1L)
                .title("제목")
                .content("본문 내용")
                .contentPlain("요약 내용")
                .contentSummary("요약 내용")
                .createdDate(now)
                .updatedDate(now)
                .build();

        // When
        PostCounts postCounts = PostCounts.create(post);

        // Then
        assertThat(postCounts.getId()).isNull();
        assertThat(postCounts.getCommentCount()).isZero();
        assertThat(postCounts.getLikeCount()).isZero();
        assertThat(postCounts.getPost().getId()).isEqualTo(post.getId());
    }
}