package com.miniblog.api.like.domain;


import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Table(name = "like_history",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"member_id", "post_id"})
        },
        indexes = {
                @Index(name = "idx_like_history_member_post", columnList = "member_id, post_id"),
                @Index(name = "idx_like_history_post_member", columnList = "post_id, member_id"),
                @Index(name = "idx_like_history_member", columnList = "member_id"),
                @Index(name = "idx_like_history_created", columnList = "created_date")
        }
)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LikeHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_history_id")
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static LikeHistory create(Member member, Post post, LocalDateTime now) {
        return LikeHistory.builder()
                .createdDate(now)
                .member(member)
                .post(post)
                .build();
    }
}
