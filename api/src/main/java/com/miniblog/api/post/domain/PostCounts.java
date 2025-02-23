package com.miniblog.api.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder(toBuilder = true)
@Getter
@Table(
        name = "post_counts",
        indexes = {
                @Index(name = "idx_post_counts_post_id", columnList = "post_id"),
                @Index(name = "idx_like_count", columnList = "like_count")
        }
)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCounts {

    @Id
    private Long id;

    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static PostCounts create(Post post) {
        return PostCounts.builder()
                .commentCount(0L)
                .likeCount(0L)
                .post(post)
                .build();
    }
}
