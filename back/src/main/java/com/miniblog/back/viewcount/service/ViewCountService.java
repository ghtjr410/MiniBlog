package com.miniblog.back.viewcount.service;

import com.miniblog.back.common.exception.NotFoundException;
import com.miniblog.back.post.model.Post;
import com.miniblog.back.viewcount.dto.request.IncrementViewCountRequestDTO;
import com.miniblog.back.viewcount.mapper.ViewCountMapper;
import com.miniblog.back.viewcount.model.ViewCount;
import com.miniblog.back.viewcount.repository.ViewCountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ViewCountService {
    private final ViewCountRepository viewCountRepository;
    private final ViewCountMapper viewCountMapper;

    @Transactional
    public void create(Post post) {
        ViewCount viewCount = viewCountMapper.create(post);
        viewCountRepository.save(viewCount);
    }

    @Transactional
    public void incrementViewCount(IncrementViewCountRequestDTO requestDTO) {

        if (!viewCountRepository.existsById(requestDTO.postId())) {
            throw  new NotFoundException("게시글을 찾을 수 없습니다.");
        }

        viewCountRepository.incrementViewCount(requestDTO.postId());
    }


}
