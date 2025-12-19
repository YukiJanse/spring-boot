package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.dto.user.UpdateUserDisplayNameDTO;
import se.jensen.yuki.springboot.dto.user.UserRequestDTO;
import se.jensen.yuki.springboot.dto.user.UserResponseDTO;
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

    public UserResponseDTO addUser(UserRequestDTO requestDTO) {
        log.info("Starting to add a user");
        User user = userMapper.toUser(requestDTO);
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new IllegalArgumentException("Username: " + user.getUsername() + " or Email: " + user.getEmail() + " already exists");
        }
        user = userRepository.save(user);
        log.info("Added a user successfully");
        return userMapper.toResponse(user);
    }

    public UserResponseDTO updateUser(Long id, UpdateUserDisplayNameDTO updateUserDTO) {
        log.info("Starting to update a user with ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to update a user with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("No user found"));
        userMapper.updateFromDisplayNameDTO(updateUserDTO, user);
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


}
