package se.jensen.yuki.springboot.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO for updating a user's email address.
 *
 * @param email           the new email address to set for the user
 * @param currentPassword the current password of the user, required for authentication
 */
public record UserUpdateEmailRequest(
        @NotBlank(message = "It can't be empty")
        @Size(min = 3, max = 60, message = "It must be between 3 - 60 letters")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "Invalid email format")
        String email, String currentPassword) {
}
