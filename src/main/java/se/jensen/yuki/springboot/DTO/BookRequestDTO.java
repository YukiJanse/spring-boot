package se.jensen.yuki.springboot.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequestDTO(
        @NotBlank(message = "Title can't be empty")
        @Size(max = 30)
        String title,

        @NotBlank(message = "Title can't be empty")
        @Size(max = 50)
        String author,

        @Min(value = 1400, message = "It must be over 1400")
        @Max(value = 2100, message = "It must be under 2100")
        int year
) {
}
