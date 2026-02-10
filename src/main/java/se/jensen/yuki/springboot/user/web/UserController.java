package se.jensen.yuki.springboot.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.user.usecase.*;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateEmailRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateProfileRequest;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final GetAllUsersUseCase getAllUsersUseCase;
    private final GetUserProfileByIdUseCase getUserProfileByIdUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UpdateEmailUseCase updateEmailUseCase;
    private final DeleteUserUseCase deleteUserUseCase;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        log.debug("Starting to get all users");

        List<UserProfileResponse> response = getAllUsersUseCase.execute();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        log.debug("Starting to get my profile");

        UserProfileResponse response = getUserProfileByIdUseCase.execute(authService.getCurrentUserId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        log.debug("Starting to get a user by ID={}", id);

        UserProfileResponse response = getUserProfileByIdUseCase.execute(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UserUpdateProfileRequest request) {
        log.debug("Stating to update my profile");

        UserProfileResponse response = updateUserProfileUseCase.execute(authService.getCurrentUserId(), request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody UserUpdateEmailRequest request) {
        log.debug("Stating to update my Account");

        authService.checkCurrentPassword(request.currentPassword());

        updateEmailUseCase.execute(authService.getCurrentUserId(), request);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest request) {
        log.debug("Stating to update my Account");

        authService.checkCurrentPassword(request.currentPassword());

        updatePasswordUseCase.execute(authService.getCurrentUserId(), request);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("Starting to delete a user by id={}", id);

        if (!id.equals(authService.getCurrentUserId())) {
            throw new IllegalArgumentException("Wrong request id=" + id);
        }

        deleteUserUseCase.execute(id);

        log.debug("Deleted a user successfully with id={}", id);
        return ResponseEntity.noContent().build();
    }
}
