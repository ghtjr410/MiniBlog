package com.miniblog.back.viewcount.repository;

import com.miniblog.back.viewcount.model.ViewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewCountRepository extends JpaRepository<ViewCount, Long>, ViewCountRepositoryCustom {
    @Modifying
    @Query("UPDATE ViewCount v SET v.count = v.count + 1 WHERE v.post.id = :postId")
    void incrementViewCount(@Param("postId") Long postId);

    void deleteByPostId(Long postId);
}
