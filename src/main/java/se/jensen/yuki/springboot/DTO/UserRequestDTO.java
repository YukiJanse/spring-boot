package se.jensen.yuki.springboot.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "It can't be empty")
        @Size(min = 3, max = 50, message = "It must be between 3 - 50 letters")
        @Pattern(regexp = "^[A-Za-z0-9 ]*$", message = "Only letters, numbers and space allowed")
        String username,

        @NotBlank(message = "It can't be empty")
        @Size(min = 3, max = 60, message = "It must be between 3 - 60 letters")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
                message = "Invalid email format")
        String email,

        @NotBlank(message = "It can't be empty")
        @Size(min = 8, max = 40, message = "It must be between 8 - 40 letters")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
                message = "Password must be at least 8 characters, include one uppercase letter, one lowercase letter and one number")
        String password,

        @NotBlank(message = "It can't be empty")
        @Size(min = 4, max = 20, message = "It must be between 4 - 20 letters")
        @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Only letters and numbers allowed")
        String role,

        @NotBlank(message = "It can't be empty")
        @Size(min = 3, max = 30, message = "It must be between 3 - 30 letters")
        @Pattern(regexp = "^[A-Za-z0-9 ]*$", message = "Only letters, numbers and space allowed")
        String displayName,

        @NotBlank(message = "It can't be empty")
        @Size(min = 1, max = 200, message = "It must be between 1 - 200 letters")
        @Pattern(regexp = "^[A-Za-z0-9 ,.]*$", message = "Only letters, numbers, space, \",\" and \".\" allowed")
        String bio,

        @Size(max = 30, message = "It must be max 30 letters")
        @Pattern(regexp = "^/(?:[^/\0]+/)*[^/\0]*$",
                message = "Only letters, numbers, space, \",\" and \".\" allowed")
        String profileImagePath
) {
}
