package se.jensen.yuki.springboot.repository;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.jensen.yuki.springboot.dto.comment.CommentDetailResponse;
import se.jensen.yuki.springboot.dto.comment.CommentReplyResponse;
import se.jensen.yuki.springboot.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("""
                SELECT new se.jensen.yuki.springboot.dto.comment.CommentDetailResponse(
                    c.id, c.content, c.createdAt, u.id, u.username, COUNT(DISTINCT cl.id), COUNT(DISTINCT child.id)
                    )
                FROM Comment c
                JOIN c.author u
                LEFT JOIN CommentLike cl ON cl.comment = c
                LEFT JOIN Comment child ON child.parent = c
                WHERE c.post.id = :postId
                AND c.parent IS NULL
                GROUP BY c.id, u.id
                ORDER BY c.createdAt ASC
            """)
    @Nullable
    Slice<CommentDetailResponse> findCommentsByPostId(@Param("postId") Long postId, Pageable pageable);

    @Nullable
    @Query("""
                SELECT new se.jensen.yuki.springboot.dto.comment.CommentReplyResponse(
                    c.id, c.content, c.createdAt, u.id, u.username, COUNT(DISTINCT cl.id), COUNT(DISTINCT child.id)
                    )
                FROM Comment c
                JOIN c.author u
                LEFT JOIN CommentLike cl ON cl.comment = c
                LEFT JOIN Comment child ON child.parent = c
                WHERE child.parent.id = :parentId
                AND c.deleted = false
                GROUP BY c.id, u.id
                ORDER BY c.createdAt ASC                
            """)
    List<CommentReplyResponse> findByParentId(@Param("parentId") Long parentId);

    @Query("""
                SELECT new se.jensen.yuki.springboot.dto.comment.CommentDetailResponse(
                    c.id, c.content, c.createdAt, u.id, u.username, COUNT(DISTINCT cl.id), COUNT(DISTINCT child.id)
                    )
                FROM Comment c
                JOIN c.author u
                LEFT JOIN CommentLike cl ON cl.comment = c
                LEFT JOIN Comment child ON child.parent = c
                WHERE c.id = :commentId
                GROUP BY c.id, u.id
            """)
    CommentDetailResponse findCommentById(@Param("commentId") Long commentId);
}
