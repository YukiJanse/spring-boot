package se.jensen.yuki.springboot.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.DTO.user.UpdateUserDisplayNameDTO;
import se.jensen.yuki.springboot.DTO.user.UserResponseDTO;
import se.jensen.yuki.springboot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("Starting to get all users");
        return ResponseEntity.ok().body(service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        log.info("Starting to get a user by ID={}", id);
        return ResponseEntity
                .ok()
                .body(service.getUserById(id));
    }

    @PutMapping("/{id}/display-name")
    public ResponseEntity<UserResponseDTO> updateUserDisplayName(@PathVariable Long id, @Valid @RequestBody UpdateUserDisplayNameDTO request) {
        log.info("Stating to update user by ID={}", id);
        return ResponseEntity
                .ok()
                .body(service.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Starting to delete a user by id={}", id);
        service.deleteUser(id);
        log.info("Deleted a user successfully with id={}", id);
        return ResponseEntity.noContent().build();
    }
}
