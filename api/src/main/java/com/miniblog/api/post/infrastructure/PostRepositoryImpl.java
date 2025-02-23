package com.miniblog.api.post.infrastructure;

import com.miniblog.api.post.application.port.PostRepository;
import com.miniblog.api.post.domain.Post;
import com.miniblog.api.post.infrastructure.dsl.PostDSLRepository;
import com.miniblog.api.post.infrastructure.persistence.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final PostDSLRepository postDSLRepository;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id);
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.delete(post);
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> postIds) {
        postJpaRepository.deleteAllByIdInBatch(postIds);
    }

    @Override
    public List<Long> findIdsByMemberId(Long memberId) {
        return postDSLRepository.findIdsByMemberId(memberId);
    }
}
