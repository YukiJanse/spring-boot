package se.jensen.yuki.springboot.DTO;

import java.time.LocalDateTime;

public record PostResponseDTO(Long id, String text, LocalDateTime createAt) {
}
