package se.jensen.yuki.springboot.user.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateProfileRequest(
        @Size(min = 3, max = 30, message = "It must be between 3 - 30 letters")
        @Pattern(regexp = "^[\\p{L}\\p{N} _.-]{2,29}$", message = "Display name must be 3–30 characters and contain only letters, numbers, spaces, \".\", \"_\" or \"-\".")
        String displayName,

        @NotBlank(message = "It can't be empty")
        @Size(min = 1, max = 200, message = "It must be between 1 - 200 letters")
        @Pattern(regexp = "^[\\p{L}\\p{N} ,.?!'\"\\-()]*$", message = "Allowed characters: letters, numbers, spaces, and .,?!'\"-()")
        String bio,

        @Size(max = 30, message = "It must be max 30 letters")
        @Pattern(regexp = "^/(?:[^/\0]+/)*[^/\0]*$",
                message = "Only letters, numbers, space, \",\" and \".\" allowed")
        String avatarUrl) {
}
