package com.miniblog.api.comment.domain;

import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.domain.Post;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CommentTest {

    private final LocalDateTime now = LocalDateTime.now();
    private final Post post = Post.builder().id(1L).build();
    private final Member member = Member.builder().id(1L).build();


    @Test
    void 댓글을_정상적으로_생성한다() {
        // Given
        String content = "댓글 내용";

        // When
        Comment comment = Comment.create(content, now, member, post);

        // Then
        assertThat(comment.getId()).isNull();
        assertThat(comment.getContent()).isEqualTo(content);
        assertThat(comment.getCreatedDate()).isEqualTo(now);
        assertThat(comment.getUpdatedDate()).isEqualTo(now);
        assertThat(comment.getMember().getId()).isEqualTo(member.getId());
        assertThat(comment.getPost().getId()).isEqualTo(post.getId());
    }

    @Test
    void 댓글을_수정하면_업데이트_날짜가_변경된다() {
        // Given
        Comment comment = Comment.builder()
                .id(1L)
                .content("원래 댓글 내용")
                .createdDate(now)
                .updatedDate(now)
                .member(member)
                .post(post)
                .build();

        String updatedContent = "수정된 댓글 내용";
        LocalDateTime updateTime = now.plusHours(1);

        // When
        comment.updateContent(updatedContent, updateTime);

        // Then
        assertThat(comment.getContent()).isEqualTo(updatedContent);
        assertThat(comment.getUpdatedDate()).isEqualTo(updateTime);
    }

    @Test
    void 댓글_소유자가_맞다면_검증에_성공한다() {
        // Given
        Comment comment = Comment.builder()
                .id(1L)
                .content("댓글 내용")
                .createdDate(now)
                .updatedDate(now)
                .member(member)
                .post(post)
                .build();

        // When & Then
        assertThatCode(() -> comment.validateOwnership(member.getId()))
                .doesNotThrowAnyException();
    }

    @Test
    void 댓글_소유자가_아니라면_예외가_발생한다() {
        // Given
        Comment comment = Comment.builder()
                .id(1L)
                .content("댓글 내용")
                .createdDate(now)
                .updatedDate(now)
                .member(member)
                .post(post)
                .build();

        Member otherMember = Member.builder().id(2L).build();

        // When & Then
        assertThatThrownBy(() -> comment.validateOwnership(otherMember.getId()))
                .isInstanceOf(ResourceUnauthorizedException.class);
    }
}