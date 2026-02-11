package se.jensen.yuki.springboot.user.infrastructure.jpa;

import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.user.domain.User;

@Component
public class UserJpaMapper {
    public void toEntity(User user, UserJpaEntity entity) {
        entity.setUsername(user.getUsername());
        entity.setEmail(user.getEmail());
        entity.setPassword(user.getPassword());
        entity.setRole(user.getRole());
        entity.setDisplayName(user.getDisplayName());
        entity.setBio(user.getBio());
        entity.setAvatarUrl(user.getAvatarUrl());
    }

    public User toDomain(UserJpaEntity user) {
        return User.reconstruct(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.getDisplayName(),
                user.getBio(),
                user.getAvatarUrl()
        );
    }
}
