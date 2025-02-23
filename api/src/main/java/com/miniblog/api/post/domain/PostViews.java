package com.miniblog.api.post.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Entity
@Table(
        name = "post_views",
        indexes = {
                @Index(name = "idx_post_views_post_id", columnList = "post_id"),
                @Index(name = "idx_view_count", columnList = "view_count")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostViews {

    @Id
    private Long id;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public static PostViews create(Post post) {
        return PostViews.builder()
                .viewCount(0L)
                .post(post)
                .build();
    }
}
