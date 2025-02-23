package com.miniblog.api.query.application;

import com.miniblog.api.query.dto.common.SearchCondition;
import com.miniblog.api.query.dto.query.PostCardDto;
import com.miniblog.api.query.infrastructure.PostSearchQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchQueryService {
    private final PostSearchQueryRepository postSearchQueryRepository;

    public Slice<PostCardDto> searchAllWithFullText(SearchCondition condition, Pageable pageable) {
        return postSearchQueryRepository.searchAllWithFullText(condition, pageable);
    }

    public Slice<PostCardDto> searchByMemberWithLike(SearchCondition condition, Long memberId, Pageable pageable) {
        return postSearchQueryRepository.searchByMemberWithLike(condition, memberId, pageable);
    }
}
