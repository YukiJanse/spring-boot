package se.jensen.yuki.springboot.user.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    Optional<UserJpaEntity> findByUsername(String username);

    Optional<UserJpaEntity> findByEmail(String email);
}
