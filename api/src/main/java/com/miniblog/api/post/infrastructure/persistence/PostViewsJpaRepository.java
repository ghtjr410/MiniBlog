    package com.miniblog.api.post.infrastructure.persistence;

    import com.miniblog.api.post.domain.PostViews;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Modifying;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    public interface PostViewsJpaRepository extends JpaRepository<PostViews, Long> {

        @Modifying
        @Query("DELETE FROM PostViews p WHERE p.id = :postId")
        void deleteByPostId(@Param("postId") Long postId);

        @Modifying
        @Query("DELETE FROM PostViews p WHERE p.id IN :postIds")
        void bulkDeleteByPostIds(@Param("postIds")List<Long> postIds);
    }
