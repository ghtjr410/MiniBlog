package com.miniblog.api.comment.domain;

import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.miniblog.api.comment.domain.CommentErrorMessage.COMMENT_NOT_OWNER;

@Builder
@Getter
@Entity
@Table(
        name = "comments",
        indexes = {
                @Index(name = "idx_comment_post_id", columnList = "post_id"),
                @Index(name = "idx_comment_member_id", columnList = "member_id"),
                @Index(name = "idx_comment_post_id_created_date_desc", columnList = "post_id, created_date DESC")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static Comment create(String content, LocalDateTime now, Member member, Post post) {
        return Comment.builder()
                .content(content)
                .createdDate(now)
                .updatedDate(now)
                .member(member)
                .post(post)
                .build();
    }

    public void updateContent(String content, LocalDateTime now) {
        this.content = content;
        this.updatedDate = now;
    }

    public void validateOwnership(long memberId) {
        if (this.member.getId() != memberId) {
            throw new ResourceUnauthorizedException(COMMENT_NOT_OWNER);
        }
    }
}
