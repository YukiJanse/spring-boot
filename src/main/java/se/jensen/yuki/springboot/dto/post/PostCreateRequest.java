package se.jensen.yuki.springboot.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostCreateRequest(
        @NotBlank(message = "it can't be empty")
        @Size(max = 200)
        String content
) {
}
