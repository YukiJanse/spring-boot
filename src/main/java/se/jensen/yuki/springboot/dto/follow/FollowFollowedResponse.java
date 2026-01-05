package se.jensen.yuki.springboot.dto.follow;

import java.time.Instant;

public record FollowFollowedResponse(Long id,
                                     String username,
                                     String displayName,
                                     String avatarUrl,
                                     String bio,
                                     Boolean followingBack,
                                     Instant followedAt) {
}
