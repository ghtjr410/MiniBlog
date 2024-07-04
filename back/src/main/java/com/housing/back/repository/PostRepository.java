package com.housing.back.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.housing.back.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByNickname(String nickname);

    Page<PostEntity> findAll(Pageable pageable);
    
    
}
