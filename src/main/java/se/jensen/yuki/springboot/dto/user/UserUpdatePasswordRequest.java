package se.jensen.yuki.springboot.dto.user;

public record UserUpdatePasswordRequest(String newPassword, String currentPassword) {
}
