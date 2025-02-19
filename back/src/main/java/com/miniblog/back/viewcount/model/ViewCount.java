package com.miniblog.back.viewcount.model;

import com.miniblog.back.post.model.Post;
import com.miniblog.back.viewcount.listener.ViewCountListener;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(ViewCountListener.class)
@Table(
        name = "view_count",
        indexes = {
                @Index(name = "idx_post_id", columnList = "post_id")
        }
)
public class ViewCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(name = "count")
    private Long count;
}
