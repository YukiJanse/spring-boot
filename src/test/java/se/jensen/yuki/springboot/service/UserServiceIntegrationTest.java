package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.user.UserProfileResponse;
import se.jensen.yuki.springboot.dto.user.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.dto.user.UserUpdateProfileRequest;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.model.User;
import se.jensen.yuki.springboot.repository.UserRepository;

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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .username("testuser")
                .email("test@test.com")
                .password(passwordEncoder.encode("password"))
                .role("USER")
                .displayName("Test User")
                .bio("test bio")
                .avatarUrl("avatar.png")
                .build();

        testUser = userRepository.save(testUser);
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
        User updated =
                userRepository.findById(testUser.getId()).get();

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

        User updated =
                userRepository.findById(testUser.getId()).get();

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

        Optional<User> deleted =
                userRepository.findById(testUser.getId());

        assertThat(deleted).isEmpty();
    }
}

