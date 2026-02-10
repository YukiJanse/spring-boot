package se.jensen.yuki.springboot.user.usecase;

import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;

/**
 * Temporary Service for migration
 * Will be removed after QueryService is fully adopted.
 */
public interface UserLoadService {
    UserJpaEntity loadById(Long id);

    UserJpaEntity loadByEmail(String email);
}
