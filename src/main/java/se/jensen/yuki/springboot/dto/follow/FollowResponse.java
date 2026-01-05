package se.jensen.yuki.springboot.dto.follow;


public record FollowResponse(boolean following,
                             boolean mutual,
                             long followerCount,
                             long followingCount,
                             Long otherId,
                             String otherUsername,
                             String otherDisplayName,
                             String otherAvatarUrl) {
}
