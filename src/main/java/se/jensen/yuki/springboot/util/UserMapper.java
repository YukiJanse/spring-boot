package se.jensen.yuki.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.DTO.UserRequestDTO;
import se.jensen.yuki.springboot.DTO.UserResponseDTO;
import se.jensen.yuki.springboot.model.User;

@Component
public class UserMapper {
    private static final Logger log = LoggerFactory.getLogger(UserMapper.class);

    public User toUser(UserRequestDTO requestDTO) {
        log.info("Mapping User from request (username={}, password={}, role={})", requestDTO.username(), requestDTO.password(), requestDTO.role());
        return new User(requestDTO.username(), requestDTO.email(), requestDTO.password(),
                requestDTO.role(), requestDTO.displayName(), requestDTO.bio(), requestDTO.profileImagePath());
    }

    public UserResponseDTO toResponse(User user) {
        log.info("Mapping Response from User (id={}, username={}, role={})", user.getId(), user.getUsername(), user.getRole());
        return new UserResponseDTO(user.getId(), user.getUsername(), user.getEmail(),
                user.getRole(), user.getDisplayName(), user.getBio(), user.getProfileImagePath());
    }
}
