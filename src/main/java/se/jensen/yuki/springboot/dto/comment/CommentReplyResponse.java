package se.jensen.yuki.springboot.dto.comment;

import java.time.Instant;

public record CommentReplyResponse(Long id,
                                   String content,
                                   Instant createdAt,
                                   Long authorUserId,
                                   String authorUsername,
                                   long likeCount,
                                   long commentCount) {
}
