package se.jensen.yuki.springboot.user.usecase;

import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;

public interface UserLoadService {
    UserJpaEntity loadById(Long id);

    UserJpaEntity loadByEmail(String email);
}
