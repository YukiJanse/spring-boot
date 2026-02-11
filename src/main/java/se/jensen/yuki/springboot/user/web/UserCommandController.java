package se.jensen.yuki.springboot.user.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.user.usecase.command.DeleteUserUseCase;
import se.jensen.yuki.springboot.user.usecase.command.UpdateEmailUseCase;
import se.jensen.yuki.springboot.user.usecase.command.UpdatePasswordUseCase;
import se.jensen.yuki.springboot.user.usecase.command.UpdateUserProfileUseCase;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateEmailRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateProfileRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserCommandController {
    private final AuthService authService;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final UpdatePasswordUseCase updatePasswordUseCase;
    private final UpdateEmailUseCase updateEmailUseCase;
    private final DeleteUserUseCase deleteUserUseCase;


    @PutMapping("/me/profile")
    public ResponseEntity<UserProfileResponse> updateProfile(@Valid @RequestBody UserUpdateProfileRequest request) {
        log.debug("Stating to update my profile");

        UserProfileResponse response = updateUserProfileUseCase.execute(authService.getCurrentUserId(), request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/me/email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody UserUpdateEmailRequest request) {
        log.debug("Stating to update my Account");

        updateEmailUseCase.execute(authService.getCurrentUserId(), request);

        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserUpdatePasswordRequest request) {
        log.debug("Stating to update my Account");

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
