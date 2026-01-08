package se.jensen.yuki.springboot.repository;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.block.BlockBlockingResponse;
import se.jensen.yuki.springboot.dto.block.BlockResponse;
import se.jensen.yuki.springboot.model.Block;

public interface BlockRepository extends JpaRepository<Block, Long> {
    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.block.BlockBlockingResponse(
                        u.id,
                        u.username,
                        u.displayName,
                        u.avatarUrl
                        )
            FROM Block b
            JOIN b.blocked u
            WHERE b.blocking.id = :currentUserId
            ORDER BY b.createdAt DESC
            """)
    @Nullable
    Slice<BlockBlockingResponse> findBlockingsByUserId(@Param("currentUserId") Long currentUserId, Pageable pageable);

    boolean existsByBlockingIdAndBlockedId(Long currentUserId, Long targetUserId);

    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.block.BlockResponse(
                        u.id,
                        u.username,
                        u.displayName,
                        u.avatarUrl
                        )
            FROM Block b
            JOIN b.blocked u
            WHERE b.blocking.id = :currentUserId AND b.blocked.id = :targetUserId
            """)
    @Nullable
    BlockResponse findByBlockingIdAndBlockedId(Long currentUserId, Long targetUserId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO blocks (blocking_id, blocked_id, created_at) VALUES (:currentUserId, :targetUserId, CURRENT_TIMESTAMP)", nativeQuery = true)
    void createBlockRelationship(@Param("currentUserId") Long currentUserId, @Param("targetUserId") Long targetUserId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM blocks WHERE blocking_id = :currentUserId AND blocked_id = :targetUserId", nativeQuery = true)
    void deleteBlockRelationship(@Param("currentUserId") Long currentUserId, @Param("targetUserId") Long targetUserId);
}
