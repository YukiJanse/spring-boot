package se.jensen.yuki.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.yuki.springboot.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(Long userId);
}
