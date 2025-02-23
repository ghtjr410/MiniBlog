package com.miniblog.api.post.domain;

import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostViewsTest {

    @Test
    void 게시글_조회수를_정상적으로_생성한다() {
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
        PostViews postViews = PostViews.create(post);

        // Then
        assertThat(postViews.getId()).isNull();
        assertThat(postViews.getViewCount()).isZero();
        assertThat(postViews.getPost().getId()).isEqualTo(post.getId());
    }
}