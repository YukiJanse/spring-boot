package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;

@Service
@RequiredArgsConstructor
public class UserLoadServiceImpl implements UserLoadService {
    private final UserJpaRepository userJpaRepository;

    @Override
    public UserJpaEntity loadById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id " + id));
    }

    @Override
    public UserJpaEntity loadByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by email " + email));
    }
}
