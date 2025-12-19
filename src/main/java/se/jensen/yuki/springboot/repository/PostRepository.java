package se.jensen.yuki.springboot.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import se.jensen.yuki.springboot.dto.post.PostFeedResponse;
import se.jensen.yuki.springboot.model.Post;

import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("""
                SELECT new se.jensen.yuki.springboot.dto.post.PostFeedResponse(
                    p.id,
                    p.content,
                    p.createdAt,
                    u.id,
                    u.username,
                    COUNT(DISTINCT pl.id),
                    COUNT(DISTINCT c.id)
                )
                FROM Post p
                JOIN p.user u
                LEFT JOIN PostLike pl ON pl.post = p
                LEFT JOIN Comment c ON c.post = p
                WHERE u.id = :userId
                    OR u.id IN (
                        SELECT f.follower.id FROM Follow f WHERE f.follower.id = :userId
                    )
                GROUP BY p.id, u.id
                ORDER BY p.createdAt DESC
            """)
    Slice<PostFeedResponse> findFeedPosts(@Param("userId") Long userId, Pageable pageable);

    @Query("""
                SELECT new se.jensen.yuki.springboot.dto.post.PostFeedResponse(
                    p.id,
                    p.content,
                    p.createdAt,
                    u.id,
                    u.username,
                    COUNT(DISTINCT pl.id),
                    COUNT(DISTINCT c.id)
                )
                FROM Post p
                JOIN p.user u
                LEFT JOIN PostLike pl ON pl.post = p
                LEFT JOIN Comment c ON c.post = p
                WHERE u.id = :userId
                GROUP BY p.id, u.id
                ORDER BY p.createdAt DESC            
            """)
    Slice<PostFeedResponse> findUserPosts(@Param("userId") Long userId, Pageable pageable);

    @Query("""
                SELECT new se.jensen.yuki.springboot.dto.post.PostFeedResponse(
                    p.id,
                    p.content,
                    p.createdAt,
                    u.id,
                    u.username,
                    COUNT(DISTINCT pl.id),
                    COUNT(DISTINCT c.id)
                )
                FROM Post p
                JOIN p.user u
                LEFT JOIN PostLike pl ON pl.post = p
                LEFT JOIN Comment c ON c.post = p
                WHERE p.id = :postId
                GROUP BY p.id, u.id         
            """)
    Optional<PostFeedResponse> findPostById(@Param("postId") Long postId);
}
