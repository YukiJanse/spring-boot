package se.jensen.yuki.springboot.repository;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.jensen.yuki.springboot.dto.like.CommentLikeResponse;
import se.jensen.yuki.springboot.model.CommentLike;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByCommentIdAndUserId(Long commentId, Long userId);

    long countByCommentId(Long commentId);

    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.like.CommentLikeResponse(
                cl.comment.id, cl.user.id, u.username, u.displayName, u.avatarUrl, cl.createdAt)
            FROM CommentLike cl
            JOIN cl.user u
            WHERE cl.comment.id = :commentId
            ORDER BY cl.createdAt DESC
            """)
    @Nullable
    Slice<CommentLikeResponse> findCommentLikesByCommentId(@Param("commentId") Long commentId, Pageable pageable);
}
