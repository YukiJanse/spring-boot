package se.jensen.yuki.springboot.dto.mute;

public record MuteResponse(Long mutedUserId,
                           String mutedUsername,
                           String mutedDisplayName,
                           String mutedAvatarUrl) {
}
