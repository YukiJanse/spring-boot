package se.jensen.yuki.springboot.repository;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.follow.FollowFollowedResponse;
import se.jensen.yuki.springboot.dto.follow.FollowFollowerResponse;
import se.jensen.yuki.springboot.dto.follow.FollowResponse;
import se.jensen.yuki.springboot.model.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    boolean existsByFollowerIdAndFollowedId(Long followerId, Long followedId);

    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.follow.FollowFollowerResponse(
                        u.id,
                        u.username,
                        u.displayName,
                        u.avatarUrl,
                        u.bio,
                        CASE WHEN :currentUserId IS NULL THEN false
                        WHEN (SELECT COUNT(f2) FROM Follow f2 WHERE f2.follower.id = :currentUserId AND f2.followed = u) > 0 THEN true
                        ELSE false END,
                        CASE WHEN :currentUserId IS NULL THEN false
                        WHEN (SELECT COUNT(f3) FROM Follow f3 WHERE f3.followed.id = :currentUserId AND f3.follower = u) > 0 THEN true
                        ELSE false END,
                        f.createdAt)
            FROM Follow f
            JOIN f.follower u
            WHERE f.followed.id = :targetUserId
            ORDER BY f.createdAt DESC
            """)
    @Nullable
    Slice<FollowFollowerResponse> findFollowersByUserId(@Param("currentUserId") Long currentUserId,
                                                        @Param("targetUserId") Long targetUserId,
                                                        Pageable pageable);

    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.follow.FollowFollowedResponse(
                        u.id,
                        u.username,
                        u.displayName,
                        u.avatarUrl,
                        u.bio,
                        CASE WHEN :currentUserId IS NULL THEN false
                        WHEN (SELECT COUNT(f2) FROM Follow f2 WHERE f2.followed.id = :currentUserId AND f2.follower = u) > 0 THEN true
                        ELSE false END,
                        f.createdAt)
            FROM Follow f
            JOIN f.followed u
            WHERE f.follower.id = :targetUserId
            ORDER BY f.createdAt DESC
            """)
    @Nullable
    Slice<FollowFollowedResponse> findFollowedByUserId(@Param("currentUserId") Long currentUserId,
                                                       @Param("targetUserId") Long targetUserId,
                                                       Pageable pageable);

    @Nullable
    @Query("""
                    SELECT new se.jensen.yuki.springboot.dto.follow.FollowResponse(
                CASE WHEN (SELECT COUNT(f2) FROM Follow f2 WHERE f2.follower.id = :currentUserId AND f2.followed.id = :userId) > 0 THEN true ELSE false END,
                CASE WHEN (SELECT COUNT(f3) FROM Follow f3 WHERE f3.follower.id = :userId AND f3.followed.id = :currentUserId) > 0 THEN true ELSE false END,
                (SELECT COUNT(f4) FROM Follow f4 WHERE f4.followed.id = :userId),
                (SELECT COUNT(f5) FROM Follow f5 WHERE f5.follower.id = :userId),
                u.id,
                u.username,
                u.displayName,
                u.avatarUrl)
                FROM UserJpaEntity u
                WHERE u.id = :userId
            """)
    FollowResponse findByFollowerIdAndFollowedId(@Param("currentUserId") Long currentUserId, @Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO follows (follower_id, followed_id, created_at, updated_at) VALUES (:currentUserId, :targetUserId, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", nativeQuery = true)
    void createFollowRelationship(@Param("currentUserId") Long currentUserId, @Param("targetUserId") Long targetUserId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM follows WHERE follower_id = :currentUserId AND followed_id = :targetUserId", nativeQuery = true)
    void deleteByFollowerIdAndFollowedId(@Param("currentUserId") Long currentUserId, @Param("targetUserId") Long targetUserId);
}
