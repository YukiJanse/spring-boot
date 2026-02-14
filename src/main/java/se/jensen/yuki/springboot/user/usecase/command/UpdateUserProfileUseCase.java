package se.jensen.yuki.springboot.user.usecase.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.DisplayName;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateProfileRequest;
import se.jensen.yuki.springboot.user.web.mapper.UserResponseMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {
    private final UserRepository userRepository;
    private final UserResponseMapper userMapper;

    @Transactional
    public UserProfileResponse execute(Long id, UserUpdateProfileRequest request) {
        log.debug("Starting to update a user with ID={}", id);

        User user = userRepository.findById(id);

        DisplayName displayName = DisplayName.of(request.displayName());

        user.updateProfile(displayName, request.bio(), request.avatarUrl());

        userRepository.save(user);

        log.debug("Updated a user successfully with ID={}", id);
        return userMapper.toResponse(user);
    }
}
