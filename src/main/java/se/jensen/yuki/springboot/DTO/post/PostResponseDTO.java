package se.jensen.yuki.springboot.DTO.post;

import java.time.LocalDateTime;

public record PostResponseDTO(Long id, String text, LocalDateTime createAt, Long userId) {
}
