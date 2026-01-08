package se.jensen.yuki.springboot.dto.mute;

public record MuteMutingResponse(Long mutedUserId,
                                 String mutedUsername,
                                 String mutedDisplayName,
                                 String mutedAvatarUrl) {
}
