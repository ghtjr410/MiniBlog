package com.miniblog.back.post.service;

import com.miniblog.back.post.mapper.PostAggregateMapper;
import com.miniblog.back.post.model.Post;
import com.miniblog.back.post.model.PostAggregate;
import com.miniblog.back.post.repository.PostAggregateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostAggregateService {
    private final PostAggregateRepository postAggregateRepository;
    private final PostAggregateMapper postAggregateMapper;

    public void create(Post post) {
        PostAggregate postAggregate = postAggregateMapper.create(post.getId());
        postAggregateRepository.save(postAggregate);
    }
}
