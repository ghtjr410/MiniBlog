package com.miniblog.api.post.application.business;

import com.miniblog.api.common.application.port.ClockHolder;
import com.miniblog.api.member.application.event.MemberActivityEventPublisher;
import com.miniblog.api.post.application.support.PostContentExtractor;
import com.miniblog.api.post.application.support.PostReader;
import com.miniblog.api.post.application.dto.ContentExtractResult;
import com.miniblog.api.post.application.dto.PostEditData;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostEditService {

    private final PostReader postReader;
    private final ClockHolder clockHolder;
    private final PostContentExtractor postContentExtractor;
    private final MemberActivityEventPublisher memberActivityEventPublisher;

    /**
     * - 게시글 본문이 변경된 경우 HTML 본문 내용 추출하여 갱신
     * - 게시글 소유권 검증 후 수정
     */
    public void editPost(PostEditData data) {
        Post post = postReader.getById(data.postId());
        post.validateOwnership(data.memberId());

        // 본문이 변경된 경우에만 HTML 내용 다시 추출
        ContentExtractResult extractContent = postContentExtractor.extractHtmlIfChanged(post, data.content());

        post.update(data, extractContent, clockHolder.now());

        memberActivityEventPublisher.publishActivity(data.memberId());
    }
}
