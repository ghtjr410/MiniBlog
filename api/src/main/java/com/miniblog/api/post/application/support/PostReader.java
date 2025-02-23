package com.miniblog.api.post.application.support;

import com.miniblog.api.common.domain.exception.ResourceNotFoundException;
import com.miniblog.api.post.application.port.PostRepository;
import com.miniblog.api.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.miniblog.api.post.domain.PostErrorMessage.POST_NOT_FOUND;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReader {
    private final PostRepository postRepository;

    public Post getById(long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(POST_NOT_FOUND));
    }

    public List<Long> getIdsByMemberId(long memberId) {
        return postRepository.findIdsByMemberId(memberId);
    }
}
