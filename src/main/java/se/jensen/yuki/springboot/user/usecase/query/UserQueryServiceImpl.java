package se.jensen.yuki.springboot.user.usecase.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;
import se.jensen.yuki.springboot.user.mapper.UserMapper;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserJpaRepository userJpaRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserProfileResponse> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();
    }

    @Override
    public UserProfileResponse findById(Long id) {
        return userJpaRepository.findById(id)
                .map(userMapper::toResponse)
                .orElseThrow(() -> new UserNotFoundException("User not found by id " + id));
    }

}
