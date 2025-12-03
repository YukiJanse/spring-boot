package se.jensen.yuki.springboot.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank(message = "It can't be empty")
        @Size(min = 3, max = 30, message = "It must be between 3 - 30 letters")
        @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Only letters and numbers allowed")
        String username,

        @NotBlank(message = "It can't be empty")
        @Size(min = 4, max = 12, message = "It must be between 4 - 12 letters")
        @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Only letters and numbers allowed")
        String password,

        @NotBlank(message = "It can't be empty")
        @Size(min = 4, max = 30, message = "It must be between 4 - 30 letters")
        @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Only letters and numbers allowed")
        String role
) {
}
