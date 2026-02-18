package se.jensen.yuki.springboot.user.infrastructure.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for User entities.
 */
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    /**
     * Finds a user by email.
     *
     * @param email the email of the user to find
     * @return an Optional containing the found user, or empty if no user is found with the given email
     */
    Optional<UserJpaEntity> findByEmail(String email);

    /**
     * Checks if a user exists by email.
     *
     * @param email the email to check
     * @return true if a user exists with the given email, false otherwise
     */
    boolean existsByEmail(String email);
}
