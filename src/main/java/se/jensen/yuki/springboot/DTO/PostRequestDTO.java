package se.jensen.yuki.springboot.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(
        @NotBlank(message = "it can't be empty")
        @Size(max = 200)
        String text
) {
}
