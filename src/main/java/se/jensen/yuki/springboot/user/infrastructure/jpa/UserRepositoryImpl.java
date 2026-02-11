package se.jensen.yuki.springboot.user.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;

    @Override
    public User findById(Long id) {
        return userJpaRepository.findById(id)
                .map(userJpaMapper::toDomain)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID=" + id));
    }

    @Override
    public void save(User user) {
        userJpaRepository.save(userJpaMapper.toEntity(user));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        userJpaRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(userJpaMapper::toDomain)
                .toList();
    }
}
