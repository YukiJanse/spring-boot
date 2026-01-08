package se.jensen.yuki.springboot.repository;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.mute.MuteMutingResponse;
import se.jensen.yuki.springboot.dto.mute.MuteResponse;
import se.jensen.yuki.springboot.model.Mute;

public interface MuteRepository extends JpaRepository<Mute, Long> {
    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.mute.MuteMutingResponse(
                        u.id,
                        u.username,
                        u.displayName,
                        u.avatarUrl
                        )
            FROM Mute m
            JOIN m.muted u
            WHERE m.muting.id = :currentUserId
            ORDER BY m.createdAt DESC
            """)
    @Nullable
    Slice<MuteMutingResponse> findMutingsByUserId(@Param("currentUserId") Long currentUserId, Pageable pageable);

    boolean existsByMutingIdAndMutedId(Long currentUserId, Long targetUserId);

    @Query("""
            SELECT new se.jensen.yuki.springboot.dto.mute.MuteResponse(
                        u.id,
                        u.username,
                        u.displayName,
                        u.avatarUrl
                        )
            FROM Mute m
            JOIN m.muted u
            WHERE m.muting.id = :currentUserId AND m.muted.id = :targetUserId
            """)
    @Nullable
    MuteResponse findByMutingIdAndMutedId(Long currentUserId, Long targetUserId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO mutes (muting_id, muted_id, created_at) VALUES (:currentUserId, :targetUserId, CURRENT_TIMESTAMP)", nativeQuery = true)
    void createMuteRelationship(@Param("currentUserId") Long currentUserId, @Param("targetUserId") Long targetUserId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM mutes WHERE muting_id = :currentUserId AND muted_id = :targetUserId", nativeQuery = true)
    void deleteMuteRelationship(@Param("currentUserId") Long currentUserId, @Param("targetUserId") Long targetUserId);
}
