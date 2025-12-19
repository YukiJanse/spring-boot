package se.jensen.yuki.springboot.dto.auth;

public record JwtResponseDTO(
        String token,
        String type // t.ex. "Bearer"
) {
}
