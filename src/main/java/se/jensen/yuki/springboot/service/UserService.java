package se.jensen.yuki.springboot.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.dto.user.UserResponseDTO;
import se.jensen.yuki.springboot.dto.user.UserUpdateEmailRequest;
import se.jensen.yuki.springboot.dto.user.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.dto.user.UserUpdateProfileRequest;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.mapper.UserMapper;
import se.jensen.yuki.springboot.model.User;
import se.jensen.yuki.springboot.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers() {
        log.info("Starting to get all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        log.info("Starting to get a user by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to get a user with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }
        return userRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public UserResponseDTO updateProfile(Long id, UserUpdateProfileRequest request) {
        log.info("Starting to update a user with ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to update a user with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found"));
        userMapper.FromUpdateProfileRequest(request, user);
        User renewedUser = userRepository.save(user);
        log.info("Updated a user successfully with ID={}", id);
        return userMapper.toResponse(renewedUser);
    }

    public void deleteUser(Long id) {
        log.info("Starting to delete a user by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to delete a user with invalid ID={}", id);
            throw new IllegalArgumentException("No users found");
        }
        userRepository.deleteById(id);
        log.info("Deleted a user successfully by ID={}", id);
    }


    public @Nullable UserResponseDTO updateEmail(Long id, @Valid UserUpdateEmailRequest request) {
        log.info("Starting to update a user with ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to update a user with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found"));
        userMapper.FromUpdateEmailRequest(request, user);
        User renewedUser = userRepository.save(user);
        log.info("Updated a user successfully with ID={}", id);
        return userMapper.toResponse(renewedUser);
    }

    public @Nullable UserResponseDTO updatePassword(Long id, @Valid UserUpdatePasswordRequest request) {
        log.info("Starting to update a user with ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to update a user with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found"));
        user.setPassword(passwordEncoder.encode(request.newPassword()));
//        userMapper.FromUpdatePasswordRequest(request, user);
        User renewedUser = userRepository.save(user);
        log.info("Updated a user successfully with ID={}", id);
        return userMapper.toResponse(renewedUser);
    }
}
