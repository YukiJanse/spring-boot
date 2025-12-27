package se.jensen.yuki.springboot.repository;


import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.jensen.yuki.springboot.dto.like.PostLikeResponse;
import se.jensen.yuki.springboot.model.PostLike;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostIdAndUserId(Long postId, Long userId);

    long countByPostId(Long postId);

    @Nullable
    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.like.PostLikeResponse(
                pl.post.id, pl.user.id, u.username, u.displayName, u.avatarUrl, pl.createdAt)
            FROM PostLike pl
            JOIN pl.user u
            WHERE pl.post.id = :postId
            ORDER BY pl.createdAt DESC
            """)
    Slice<PostLikeResponse> findPostLikesByPostId(@Param("postId") Long postId, Pageable pageable);
}
