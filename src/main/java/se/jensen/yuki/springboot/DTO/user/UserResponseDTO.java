package se.jensen.yuki.springboot.DTO.user;

public record UserResponseDTO(Long id, String username, String email, String role, String displayName, String bio,
                              String profileImagePath) {
}
