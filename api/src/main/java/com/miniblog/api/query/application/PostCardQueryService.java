package com.miniblog.api.query.application;

import com.miniblog.api.query.dto.common.PostCardCondition;
import com.miniblog.api.query.dto.query.PostCardDto;
import com.miniblog.api.query.infrastructure.PostCardQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostCardQueryService {

    private final PostCardQueryRepository postCardQueryRepository;

    public Slice<PostCardDto> getAllPostCards(PostCardCondition condition, Pageable pageable) {
        return postCardQueryRepository.getAllOrMemberPostCards(condition, null, pageable);
    }

    public Slice<PostCardDto> getMemberPostCards(PostCardCondition condition, Long memberId, Pageable pageable) {
        return postCardQueryRepository.getAllOrMemberPostCards(condition, memberId, pageable);
    }

    public Slice<PostCardDto> getCommentedPostCards(PostCardCondition condition, Long memberId, Pageable pageable) {
        return postCardQueryRepository.getCommentedPostCards(condition, memberId, pageable);
    }

    public Slice<PostCardDto> getLikedPostCards(PostCardCondition condition, Long memberId, Pageable pageable) {
        return postCardQueryRepository.getLikedPostCards(condition, memberId, pageable);
    }
}
