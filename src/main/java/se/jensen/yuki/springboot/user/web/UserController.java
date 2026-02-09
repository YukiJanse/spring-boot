package se.jensen.yuki.springboot.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.user.usecase.UpdateUserProfileUseCase;
import se.jensen.yuki.springboot.user.usecase.UserService;
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

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        log.debug("Starting to get all users");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        log.debug("Starting to get my profile");
        return ResponseEntity
                .ok()
                .body(userService.getUserById(authService.getCurrentUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        log.debug("Starting to get a user by ID={}", id);
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id));
    }

    @PutMapping("/me/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UserUpdateProfileRequest request) {
        log.debug("Stating to update my profile");

        UserProfileResponse response = updateUserProfileUseCase.execute(authService.getCurrentUserId(), request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody UserUpdateEmailRequest request) {
        authService.checkCurrentPassword(request.currentPassword());
        log.debug("Stating to update my Account");
        userService.updateEmail(authService.getCurrentUserId(), request);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest request) {
        authService.checkCurrentPassword(request.currentPassword());
        log.debug("Stating to update my Account");
        userService.updatePassword(authService.getCurrentUserId(), request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("Starting to delete a user by id={}", id);
        Long userId = authService.getCurrentUserId();
        if (!id.equals(userId)) {
            throw new IllegalArgumentException("Wrong request id=" + id);
        }
        userService.deleteUser(id);
        log.debug("Deleted a user successfully with id={}", id);
        return ResponseEntity.noContent().build();
    }
}
