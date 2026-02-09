package se.jensen.yuki.springboot.user.usecase;

import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;

import java.util.List;


public interface UserQueryService {

    List<UserJpaEntity> findAll();

    UserJpaEntity findById(Long id);

    UserJpaEntity findByEmail(String email);
}
