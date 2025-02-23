package com.miniblog.api.post.application.business;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.support.MemberReader;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.member.domain.Member;
import com.miniblog.api.post.application.support.PostContentExtractor;
import com.miniblog.api.post.application.support.PostStatisticsInitializer;
import com.miniblog.api.post.application.dto.ContentExtractResult;
import com.miniblog.api.post.application.dto.PostWriteData;
import com.miniblog.api.post.application.port.PostRepository;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostWriteService {

    private final PostRepository postRepository;
    private final MemberReader memberReader;
    private final ClockHolder clockHolder;
    private final PostStatisticsInitializer postStatisticsInitializer;
    private final PostContentExtractor postContentExtractor;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * - 게시글을 생성하고 저장
     * - 게시글의 통계를 초기화 (조회수, 좋아요, 댓글 수)
     * - HTML 본문에서 텍스트를 추출하여 요약 및 일반 텍스트 버전 생성
     */
    public long addPost(PostWriteData data) {
        Member member = memberReader.getById(data.memberId());

        ContentExtractResult extractContent = postContentExtractor.extractHtml(data.content());

        Post post = Post.create(data, extractContent, clockHolder.now(), member);
        postRepository.save(post);

        // 게시글 통계 (조회수, 좋아요, 댓글 수) 초기화
        postStatisticsInitializer.initialize(post);

        memberActivityEventPublisher.publishActivity(member.getId());
        return post.getId();
    }
}