package se.jensen.yuki.springboot.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserDisplayNameDTO(
        @NotBlank(message = "It can't be empty")
        @Size(min = 3, max = 30, message = "It must be between 3 - 30 letters")
        @Pattern(regexp = "^[A-Za-z0-9 ]*$", message = "Only letters, numbers and space allowed")
        String displayName) {
}
