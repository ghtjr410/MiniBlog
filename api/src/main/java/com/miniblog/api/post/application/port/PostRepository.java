package com.miniblog.api.post.application.port;

import com.miniblog.api.post.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    void delete(Post post);
    void deleteAllByIdInBatch(List<Long> postIds);

    Optional<Post> findById(Long id);
    List<Long> findIdsByMemberId(Long memberId);
}
