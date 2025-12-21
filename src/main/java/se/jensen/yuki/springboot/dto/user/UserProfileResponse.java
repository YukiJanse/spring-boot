package se.jensen.yuki.springboot.dto.user;

public record UserProfileResponse(Long id, String username, String email, String role, String displayName, String bio,
                                  String avatarUrl) {
}
