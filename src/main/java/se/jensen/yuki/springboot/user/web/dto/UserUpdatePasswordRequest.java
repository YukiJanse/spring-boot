package se.jensen.yuki.springboot.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating a user's password.
 *
 * @param newPassword     the new password to set for the user
 * @param currentPassword the current password of the user, used for verification
 */
public record UserUpdatePasswordRequest(
        @NotBlank(message = "It can't be empty")
        @Size(min = 8, max = 40, message = "It must be between 8 - 40 letters")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must be at least 8 characters, include one uppercase letter, one lowercase letter and one number")
        String newPassword, String currentPassword) {
}
