package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryServiceImpl implements UserQueryService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public List<UserJpaEntity> findAll() {
        return userJpaRepository.findAll();
    }

    @Override
    public UserJpaEntity findById(Long id) {
        return userJpaRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Override
    public UserJpaEntity findByEmail(String email) {
        return userJpaRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
