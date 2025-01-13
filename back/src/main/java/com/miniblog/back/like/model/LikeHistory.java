package com.miniblog.back.like.model;

import com.miniblog.back.like.listener.LikeHistoryListener;
import com.miniblog.back.member.model.Member;
import com.miniblog.back.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(LikeHistoryListener.class)
@Table(name = "like_history")
public class LikeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
}