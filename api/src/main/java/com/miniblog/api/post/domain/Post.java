package com.miniblog.api.post.domain;

import com.miniblog.api.common.domain.exception.ResourceUnauthorizedException;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.application.dto.ContentExtractResult;
import com.miniblog.api.post.application.dto.PostWriteData;
import com.miniblog.api.post.application.dto.PostEditData;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

import static com.miniblog.api.post.domain.PostErrorMessage.POST_NOT_OWNER;

@Builder
@Getter
@Entity
@Table(
        name = "posts",
        indexes = {
                /**
                 * ALTER TABLE posts
                 * ADD FULLTEXT INDEX idx_ft_title_content_plain (title, contentPlain)
                 * WITH PARSER ngram;
                 */
                @Index(name = "idx_member_id", columnList = "member_id"),
                @Index(name = "idx_created_date", columnList = "created_date")
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "content_plain", columnDefinition = "TEXT", nullable = false)
    private String contentPlain;

    @Column(name = "content_summary", nullable = false, length = 100)
    private String contentSummary;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public static Post create(PostWriteData data, ContentExtractResult extractContent, LocalDateTime now, Member member) {
        return Post.builder()
                .title(data.title())
                .content(data.content())
                .contentSummary(extractContent.summary())
                .contentPlain(extractContent.plain())
                .createdDate(now)
                .updatedDate(now)
                .member(member)
                .build();
    }

    public void update(PostEditData data, ContentExtractResult extractContent, LocalDateTime now) {
        this.title = data.title();
        this.content = data.content();
        this.contentSummary = extractContent.summary();
        this.contentPlain = extractContent.plain();
        this.updatedDate = now;
    }

    public void validateOwnership(long memberId) {
        if (this.member.getId() != memberId) {
            throw new ResourceUnauthorizedException(POST_NOT_OWNER);
        }
    }

    public boolean hasContentChanged(String content) {
        return !StringUtils.equals(this.content, content);
    }
}
