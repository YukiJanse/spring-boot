package se.jensen.yuki.springboot.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PostRequestDTO(
        @NotNull(message = "It can't be empty")
        Long userId,

        @NotBlank(message = "it can't be empty")
        @Size(max = 200)
        String text
) {
}
