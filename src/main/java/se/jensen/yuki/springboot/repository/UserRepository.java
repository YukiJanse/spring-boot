package se.jensen.yuki.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.yuki.springboot.model.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);
}
