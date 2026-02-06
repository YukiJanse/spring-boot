package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.auth.AuthRegisterRequestDTO;
import se.jensen.yuki.springboot.dto.auth.LoginDTO;
import se.jensen.yuki.springboot.model.RefreshToken;
import se.jensen.yuki.springboot.repository.RefreshTokenRepository;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceIntegrationTest {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clean() {
        refreshTokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    // ---------- register ----------

    @Test
    void registerUser_success() {

        AuthRegisterRequestDTO dto =
                new AuthRegisterRequestDTO(
                        "testuser",
                        "test@test.com",
                        "password123"
                );

        AuthService.TokenPair tokenPair =
                authService.registerUser(dto);

        // token返ってる
        assertThat(tokenPair).isNotNull();
        assertThat(tokenPair.accessToken()).isNotBlank();
        assertThat(tokenPair.refreshToken()).isNotBlank();

        // user保存されてる
        UserJpaEntity user = userRepository
                .findByEmail("test@test.com")
                .orElseThrow();

        assertThat(passwordEncoder.matches(
                "password123",
                user.getPassword()
        )).isTrue();

        // refresh token保存されてる
        assertThat(
                refreshTokenRepository
                        .findByToken(tokenPair.refreshToken())
        ).isPresent();
    }


    // ---------- login ----------

    @Test
    void login_success() {

        // user準備
        UserJpaEntity user = UserJpaEntity.builder()
                .username("loginuser")
                .email("login@test.com")
                .password(passwordEncoder.encode("secret"))
                .role("USER")
                .displayName("loginuser")
                .bio("bio")
                .build();

        userRepository.save(user);

        LoginDTO dto =
                new LoginDTO("login@test.com", "secret");

        AuthService.TokenPair tokenPair =
                authService.login(dto);

        assertThat(tokenPair.accessToken()).isNotBlank();
        assertThat(tokenPair.refreshToken()).isNotBlank();
    }


    // ---------- refresh ----------

    @Test
    void refreshToken_rotate_success() {

        // user作成
        UserJpaEntity user = UserJpaEntity.builder()
                .username("refreshuser")
                .email("refresh@test.com")
                .password(passwordEncoder.encode("pw"))
                .role("USER")
                .displayName("refreshuser")
                .bio("bio")
                .build();

        userRepository.save(user);

        // 最初のtoken
        RefreshToken rt =
                authService.createRefreshToken(user);

        String oldToken = rt.getToken();

        // refresh
        Optional<AuthService.TokenPair> result =
                authService.refreshAccessToken(oldToken);

        assertThat(result).isPresent();

        AuthService.TokenPair pair = result.get();

        // 新token
        assertThat(pair.refreshToken())
                .isNotEqualTo(oldToken);

        // old revoked
        RefreshToken old =
                refreshTokenRepository
                        .findByToken(oldToken)
                        .orElseThrow();

        assertThat(old.isRevoked()).isTrue();
    }
}
