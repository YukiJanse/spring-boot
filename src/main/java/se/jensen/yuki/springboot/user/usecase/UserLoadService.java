package se.jensen.yuki.springboot.user.usecase;

import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;

/**
 * Temporary Service for migration
 * Will be removed after QueryService is fully adopted.
 */
public interface UserLoadService {
    /**
     * Loads a user by their unique identifier.
     *
     * @param id the unique identifier of the user
     * @return the UserJpaEntity corresponding to the given id, or null if not found
     */
    UserJpaEntity requireJpaById(Long id);

    /**
     * Loads a user by their email address.
     *
     * @param email the email address of the user
     * @return the UserJpaEntity corresponding to the given email, or null if not found
     */
    UserJpaEntity requireJpaByEmail(String email);
}
