package com.miniblog.back.post.model;

import com.miniblog.back.post.listener.PostAggregateListener;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(PostAggregateListener.class)
@Table(
        name = "post_aggregate",
        indexes = {
                @Index(name = "idx_post_id", columnList = "post_id")
        }
)
public class PostAggregate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(name = "comment_count", nullable = false)
    private Long comment_count;

    @Column(name = "like_count", nullable = false)
    private Long like_count;
}
