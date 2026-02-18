package se.jensen.yuki.springboot.user.usecase.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.UserId;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.mapper.UserResponseMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    private final UserResponseMapper userMapper;

    @Override
    public List<UserProfileResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserProfileResponse findById(Long id) {
        UserId userId = UserId.of(id);
        return userMapper.toResponse(userRepository.findById(userId));
    }

}
