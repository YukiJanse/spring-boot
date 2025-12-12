package se.jensen.yuki.springboot.DTO.auth;

public record JwtResponseDTO(
        String token,
        String type // t.ex. "Bearer"
) {
}
