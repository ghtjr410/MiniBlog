package com.miniblog.api.post.domain;

import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.application.dto.ContentExtractResult;
import com.miniblog.api.post.application.dto.PostEditData;
import com.miniblog.api.post.application.dto.PostWriteData;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PostTest {

    private final Member member = Member.builder().id(1L).build();

    @Test
    void 게시글을_정상적으로_생성한다() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        PostWriteData writeData = new PostWriteData("제목", "본문 내용", member.getId());
        ContentExtractResult extractResult = ContentExtractResult.of("요약 내용", "본문 내용");

        // When
        Post post = Post.create(writeData, extractResult, now, member);

        // Then
        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo(writeData.title());
        assertThat(post.getContent()).isEqualTo(writeData.content());
        assertThat(post.getContentSummary()).isEqualTo(extractResult.summary());
        assertThat(post.getContentPlain()).isEqualTo(extractResult.plain());
        assertThat(post.getCreatedDate()).isEqualTo(now);
        assertThat(post.getUpdatedDate()).isEqualTo(now);
        assertThat(post.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    void 게시글을_정상적으로_수정한다() {
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
                .member(member)
                .build();

        PostEditData editData = new PostEditData(post.getId(),"수정된 제목", "수정된 본문", post.getMember().getId());
        ContentExtractResult updatedExtractResult = new ContentExtractResult("수정된 요약", "수정된 본문");

        LocalDateTime updateTime = now.plusDays(1);

        // When
        post.update(editData, updatedExtractResult, updateTime);

        // Then
        assertThat(post.getTitle()).isEqualTo(editData.title());
        assertThat(post.getContent()).isEqualTo(editData.content());
        assertThat(post.getContentSummary()).isEqualTo(updatedExtractResult.summary());
        assertThat(post.getContentPlain()).isEqualTo(updatedExtractResult.plain());
        assertThat(post.getUpdatedDate()).isEqualTo(updateTime);
    }

    @Test
    void 게시글_소유자가_맞다면_검증에_성공한다() {
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
                .member(member)
                .build();

        // When & Then
        assertThatCode(() -> post.validateOwnership(member.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 게시글_소유자가_아니라면_검증에_실패한다() {
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
                .member(member)
                .build();

        // When & Then
        assertThatThrownBy(() -> post.validateOwnership(2L))
                .isInstanceOf(ResourceUnauthorizedException.class);
    }

    @Test
    void 게시글_내용이_변경되지_않았을_경우_false를_반환한다() {
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
                .member(member)
                .build();

        // When
        boolean result = post.hasContentChanged("본문 내용");

        // Then
        assertThat(result).isFalse();
    }

    @Test
    void 게시글_내용이_변경되었을_경우_true를_반환한다() {
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
                .member(member)
                .build();

        // When
        boolean result = post.hasContentChanged("새로운 본문 내용");

        // Then
        assertThat(result).isTrue();
    }
}