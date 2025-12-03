package se.jensen.yuki.springboot.service;

import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.DTO.UserRequestDTO;
import se.jensen.yuki.springboot.DTO.UserResponseDTO;
import se.jensen.yuki.springboot.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final List<User> users = new ArrayList<>();

    public List<UserResponseDTO> getAllUsers() {
        return users.stream()
                .map(this::fromUser)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        if (id == null || id < 0 || id >= users.size()) {
            throw new NoSuchElementException("No users found");
        }
        return fromUser(users.get(id.intValue()));
    }

    public UserResponseDTO addUser(UserRequestDTO requestDTO) {
        User user = fromRequest(requestDTO);
        users.add(user);
        return fromUser(user);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {
        if (id == null || id < 0 || id >= users.size()) {
            throw new NoSuchElementException("No users found");
        }
        User user = fromRequest(requestDTO);
        users.set(id.intValue(), user);
        return fromUser(users.get(id.intValue()));
    }

    public void deleteUser(Long id) {
        if (id == null || id < 0 || id >= users.size()) {
            throw new NoSuchElementException("No users found");
        }
        users.remove(id.intValue());
    }

    private User fromRequest(UserRequestDTO requestDTO) {
        return new User(0L, requestDTO.username(), requestDTO.password(), requestDTO.role());
    }

    private UserResponseDTO fromUser(User user) {
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getRole());
    }
}
