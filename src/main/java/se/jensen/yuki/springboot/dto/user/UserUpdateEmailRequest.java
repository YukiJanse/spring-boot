package se.jensen.yuki.springboot.dto.user;

public record UserUpdateEmailRequest(String email, String currentPassword) {
}
