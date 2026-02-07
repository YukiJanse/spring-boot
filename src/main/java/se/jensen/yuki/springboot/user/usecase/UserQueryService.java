package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserQueryService {
    private final UserJpaRepository userJpaRepository;

    public List<UserJpaEntity> findAll() {
        return userJpaRepository.findAll();
    }

    public Optional<UserJpaEntity> findById(Long id) {
        return userJpaRepository.findById(id);
    }

    public UserJpaEntity save(UserJpaEntity user) {
        return userJpaRepository.save(user);
    }

    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    public Optional<UserJpaEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    public void deleteAll() {
        userJpaRepository.deleteAll();
    }
}
