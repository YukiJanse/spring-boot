package se.jensen.yuki.springboot.user.web.dto;

/**
 * DTO for user profile response.
 *
 * @param id          the user's ID
 * @param username    the user's username
 * @param email       the user's email
 * @param role        the user's role
 * @param displayName the user's display name
 * @param bio         the user's bio
 * @param avatarUrl   the URL of the user's avatar
 */
public record UserProfileResponse(Long id, String username, String email, String role, String displayName, String bio,
                                  String avatarUrl) {
}
