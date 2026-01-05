package se.jensen.yuki.springboot.dto.follow;

import java.time.Instant;

public record FollowFollowerResponse(Long id,
                                     String username,
                                     String displayName,
                                     String avatarUrl,
                                     String bio,
                                     boolean following,
                                     boolean mutual,
                                     Instant followedAt) {
}
