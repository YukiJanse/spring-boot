package se.jensen.yuki.springboot.dto.comment;

import java.time.Instant;

public record CommentCreateResponse(Long id,
                                    String content,
                                    Instant createdAt,
                                    Long authorUserId,
                                    String authorUsername,
                                    Long postId,
                                    long likeCount,
                                    long commentCount) {
}
