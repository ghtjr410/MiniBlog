package com.miniblog.api.post.infrastructure.persistence;


import com.miniblog.api.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
}
