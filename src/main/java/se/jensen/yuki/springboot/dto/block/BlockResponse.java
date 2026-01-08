package se.jensen.yuki.springboot.dto.block;

public record BlockResponse(Long blockedUserId,
                            String blockedUsername,
                            String blockedDisplayName,
                            String blockedAvatarUrl) {
}