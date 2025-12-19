package se.jensen.yuki.springboot.dto.post;

import java.time.Instant;

public record PostFeedResponse(Long id,
                               String content,
                               Instant createdAt,
                               Long authorUserId,
                               String authorUsername,
                               long likeCount,
                               long commentCount) {
}
