package com.miniblog.back.comment.model;

import com.miniblog.back.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment_count")
public class CommentCount {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    private Post post;

    @Column(name = "count")
    private Long count;
}
