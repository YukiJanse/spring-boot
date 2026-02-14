package se.jensen.yuki.springboot.user.usecase.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.AvatarUrl;
import se.jensen.yuki.springboot.user.domain.vo.Bio;
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
        Bio bio = Bio.of(request.bio());
        AvatarUrl avatarUrl = AvatarUrl.of(request.avatarUrl());

        user.updateProfile(displayName, bio, avatarUrl);

        userRepository.save(user);

        log.debug("Updated a user successfully with ID={}", id);
        return userMapper.toResponse(user);
    }
}
