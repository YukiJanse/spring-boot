package se.jensen.yuki.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.DTO.UserRequestDTO;
import se.jensen.yuki.springboot.DTO.UserResponseDTO;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.model.User;
import se.jensen.yuki.springboot.repository.UserRepository;
import se.jensen.yuki.springboot.util.UserMapper;

import java.util.List;

@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final UserMapper userMapper;
    private final UserRepository repo;
    //private final List<User> users = new ArrayList<>();

    public UserService(UserMapper userMapper, UserRepository repo) {
        this.userMapper = userMapper;
        this.repo = repo;
    }

    public List<UserResponseDTO> getAllUsers() {
        log.info("Starting to get all users");
        return repo.findAll()
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
        return repo.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public UserResponseDTO addUser(UserRequestDTO requestDTO) {
        log.info("Starting to add a user");
        User user = userMapper.toUser(requestDTO);
        if (repo.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new IllegalArgumentException("Username: " + user.getUsername() + " or Email: " + user.getEmail() + " already exists");
        }
        user = repo.save(user);
        log.info("Added a user successfully");
        return userMapper.toResponse(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        log.info("Starting to update a user with ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to update a user with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }
        User user = userMapper.toUser(requestDTO);
        User currentUser = repo.findById(id).orElseThrow(() -> new UserNotFoundException("No user found"));
        currentUser.copy(user);
        User renewedUser = repo.save(currentUser);
        log.info("Updated a user successfully with ID={}", id);
        return userMapper.toResponse(renewedUser);
    }

    public void deleteUser(Long id) {
        log.info("Starting to delete a user by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to delete a user with invalid ID={}", id);
            throw new IllegalArgumentException("No users found");
        }
        repo.deleteById(id);
        log.info("Deleted a user successfully by ID={}", id);
    }


}
