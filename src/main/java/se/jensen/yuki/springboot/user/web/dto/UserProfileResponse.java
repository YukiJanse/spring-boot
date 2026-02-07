package se.jensen.yuki.springboot.user.web.dto;

public record UserProfileResponse(Long id, String username, String email, String role, String displayName, String bio,
                                  String avatarUrl) {
}
