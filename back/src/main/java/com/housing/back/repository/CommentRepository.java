package com.housing.back.repository;

import com.housing.back.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    @Transactional
    void deleteByNickname(String nickname);
}
