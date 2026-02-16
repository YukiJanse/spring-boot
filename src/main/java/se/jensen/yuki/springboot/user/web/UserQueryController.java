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

/**
 * REST controller for handling user-related queries.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserQueryController {
    private final UserQueryService userQueryService;
    private final AuthService authService;

    /**
     * Retrieves a list of all users in the system. This endpoint is intended for admin use.
     *
     * @return a ResponseEntity containing a list of UserProfileResponse objects representing all users
     */
    @GetMapping("/admin/users")
    public ResponseEntity<List<UserProfileResponse>> getAllUsers() {
        log.debug("Starting to get all users");

        List<UserProfileResponse> response = userQueryService.findAll();

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the profile of the currently authenticated user.
     *
     * @return a ResponseEntity containing a UserProfileResponse object representing the current user's profile
     */
    @GetMapping("/me")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        log.debug("Starting to get my profile");

        UserProfileResponse response = userQueryService.findById(authService.getCurrentUserId());

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a user by their ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing a UserProfileResponse object representing the user with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserProfileResponse> getUserById(@PathVariable Long id) {
        log.debug("Starting to get a user by ID={}", id);

        UserProfileResponse response = userQueryService.findById(id);

        return ResponseEntity.ok(response);
    }
}
