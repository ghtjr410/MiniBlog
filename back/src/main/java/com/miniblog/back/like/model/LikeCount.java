package com.miniblog.back.like.model;

import com.miniblog.back.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "like_count")
public class LikeCount {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(name = "count")
    private Long count;
}
