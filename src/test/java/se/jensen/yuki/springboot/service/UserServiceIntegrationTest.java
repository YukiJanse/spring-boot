package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;
import se.jensen.yuki.springboot.user.usecase.UserService;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateProfileRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private UserJpaEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = UserJpaEntity.builder()
                .username("testuser")
                .email("test@test.com")
                .password(passwordEncoder.encode("password"))
                .role("USER")
                .displayName("Test User")
                .bio("test bio")
                .avatarUrl("avatar.png")
                .build();

        testUser = userJpaRepository.save(testUser);
    }

    // ----------------------------
    // getUserById success
    // ----------------------------
    @Test
    void getUserById_success() {
        UserProfileResponse response =
                userService.getUserById(testUser.getId());

        assertThat(response).isNotNull();
        assertThat(response.username()).isEqualTo("testuser");
        assertThat(response.email()).isEqualTo("test@test.com");
    }

    // ----------------------------
    // getUserById not found
    // ----------------------------
    @Test
    void getUserById_notFound() {
        Long invalidId = 9999L;

        assertThatThrownBy(() ->
                userService.getUserById(invalidId))
                .isInstanceOf(UserNotFoundException.class);
    }

    // ----------------------------
    // updateProfile success
    // ----------------------------
    @Test
    void updateProfile_success() {
        UserUpdateProfileRequest request =
                new UserUpdateProfileRequest(
                        "New Name",
                        "New Bio",
                        "new-avatar.png"
                );

        UserProfileResponse response =
                userService.updateProfile(testUser.getId(), request);

        assertThat(response.displayName()).isEqualTo("New Name");
        assertThat(response.bio()).isEqualTo("New Bio");
        assertThat(response.avatarUrl()).isEqualTo("new-avatar.png");

        // DB確認
        UserJpaEntity updated =
                userJpaRepository.findById(testUser.getId()).get();

        assertThat(updated.getDisplayName()).isEqualTo("New Name");
    }

    // ----------------------------
    // updatePassword success
    // ----------------------------
    @Test
    void updatePassword_success() {
        UserUpdatePasswordRequest request =
                new UserUpdatePasswordRequest("newPassword123", "currentPassword123");

        userService.updatePassword(testUser.getId(), request);

        UserJpaEntity updated =
                userJpaRepository.findById(testUser.getId()).get();

        assertThat(
                passwordEncoder.matches(
                        "newPassword123",
                        updated.getPassword()
                )
        ).isTrue();
    }

    // ----------------------------
    // deleteUser success
    // ----------------------------
    @Test
    void deleteUser_success() {
        userService.deleteUser(testUser.getId());

        Optional<UserJpaEntity> deleted =
                userJpaRepository.findById(testUser.getId());

        assertThat(deleted).isEmpty();
    }
}

