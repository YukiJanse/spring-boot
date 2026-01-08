package se.jensen.yuki.springboot.dto.block;

public record BlockBlockingResponse(Long blockedUserId,
                                    String blockedUsername,
                                    String blockedDisplayName,
                                    String blockedAvatarUrl) {
}
