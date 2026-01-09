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
    String PROJECTION = """
            SELECT new se.jensen.yuki.springboot.dto.post.PostFeedResponse(
                p.id,
                p.content,
                p.createdAt,
                u.id,
                u.username,
                COUNT(DISTINCT pl.id),
                COUNT(DISTINCT c.id)
            )
            """;

    String FROM_JOINS = """
            FROM Post p
            JOIN p.user u
            LEFT JOIN PostLike pl WITH pl.post = p
            LEFT JOIN Comment c WITH c.post = p
            """;

    @Query(PROJECTION +
            FROM_JOINS +
            " WHERE (u.id = :userId " +
            "OR ( u.id IN (SELECT f.followed.id FROM Follow f WHERE f.follower.id = :userId) " +
            "AND NOT EXISTS (SELECT 1 FROM Block b WHERE b.blocking.id = :userId AND b.blocked.id = u.id) " +
            "AND NOT EXISTS (SELECT 1 FROM Block b2 WHERE b2.blocked.id = :userId AND b2.blocking.id = u.id) " +
            "AND NOT EXISTS (SELECT 1 FROM Mute m WHERE m.muting.id = :userId AND m.muted.id = u.id) )) " +
            "GROUP BY p.id, u.id " +
            "ORDER BY p.createdAt DESC")
    Slice<PostFeedResponse> findFeedPosts(@Param("userId") Long userId, Pageable pageable);

    @Query(PROJECTION +
            FROM_JOINS +
            " WHERE u.id = :userId " +
            "GROUP BY p.id, u.id " +
            "ORDER BY p.createdAt DESC")
    Slice<PostFeedResponse> findUserPosts(@Param("userId") Long userId, Pageable pageable);

    @Query(PROJECTION +
            FROM_JOINS +
            " WHERE p.id = :postId " +
            "GROUP BY p.id, u.id")
    Optional<PostFeedResponse> findPostById(@Param("postId") Long postId);
}
