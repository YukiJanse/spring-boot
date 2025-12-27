package se.jensen.yuki.springboot.dto.like;

import java.time.Instant;

public record PostLikeResponse(Long postId,
                               Long userId,
                               String username,
                               String displayName,
                               String avatarUrl,
                               Instant likedAt) {
}
