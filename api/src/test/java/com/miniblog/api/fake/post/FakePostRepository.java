package com.miniblog.api.fake.post;

import com.miniblog.api.post.application.port.PostRepository;
import com.miniblog.api.post.domain.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakePostRepository implements PostRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    @Override
    public Post save(Post post) {
        if (post.getId() == null || post.getId() == 0) {
            Long id = autoGeneratedId.incrementAndGet();
            Post newPost = post.toBuilder().id(id).build();
            posts.put(id, newPost);
            return newPost;
        } else {
            posts.put(post.getId(), post);
            return post;
        }
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    @Override
    public void delete(Post post) {
        posts.remove(post.getId());
    }

    @Override
    public void deleteAllByIdInBatch(List<Long> postIds) {
        postIds.forEach(posts::remove);
    }

    @Override
    public List<Long> findIdsByMemberId(Long memberId) {
        return posts.values().stream()
                .filter(post -> post.getMember().getId().equals(memberId))
                .map(Post::getId)
                .collect(Collectors.toList());
    }
}
