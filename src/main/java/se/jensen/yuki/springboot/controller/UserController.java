package se.jensen.yuki.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.user.UserProfileResponse;
import se.jensen.yuki.springboot.dto.user.UserUpdateEmailRequest;
import se.jensen.yuki.springboot.dto.user.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.dto.user.UserUpdateProfileRequest;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        log.info("Starting to get all users");
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        log.info("Starting to get my profile");
        return ResponseEntity
                .ok()
                .body(userService.getUserById(authService.getCurrentUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        log.info("Starting to get a user by ID={}", id);
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id));
    }

    @PutMapping("/me/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UserUpdateProfileRequest request) {
        log.info("Stating to update my profilee");
        return ResponseEntity
                .ok()
                .body(userService.updateProfile(authService.getCurrentUserId(), request));
    }

    @PutMapping("/me/email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody UserUpdateEmailRequest request) {
        authService.checkCurrentPassword(request.currentPassword());
        log.info("Stating to update my Account");
        userService.updateEmail(authService.getCurrentUserId(), request);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest request) {
        authService.checkCurrentPassword(request.currentPassword());
        log.info("Stating to update my Account");
        userService.updatePassword(authService.getCurrentUserId(), request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Starting to delete a user by id={}", id);
        Long userId = authService.getCurrentUserId();
        if (!id.equals(userId)) {
            throw new IllegalArgumentException("Wrong request id=" + id);
        }
        userService.deleteUser(id);
        log.info("Deleted a user successfully with id={}", id);
        return ResponseEntity.noContent().build();
    }
}
