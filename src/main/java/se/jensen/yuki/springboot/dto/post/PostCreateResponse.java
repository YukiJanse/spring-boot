package se.jensen.yuki.springboot.dto.post;

import java.time.LocalDateTime;

public record PostCreateResponse(Long id, String content, LocalDateTime createAt, Long userId) {
}
