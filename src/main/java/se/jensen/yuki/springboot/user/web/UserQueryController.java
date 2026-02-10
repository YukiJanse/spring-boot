package se.jensen.yuki.springboot.user.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.user.usecase.query.UserQueryService;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserQueryController {
    private final UserQueryService userQueryService;
    private final AuthService authService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        log.debug("Starting to get all users");

        List<UserProfileResponse> response = userQueryService.findAll();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        log.debug("Starting to get my profile");

        UserProfileResponse response = userQueryService.findById(authService.getCurrentUserId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        log.debug("Starting to get a user by ID={}", id);

        UserProfileResponse response = userQueryService.findById(id);

        return ResponseEntity.ok(response);
    }
}
