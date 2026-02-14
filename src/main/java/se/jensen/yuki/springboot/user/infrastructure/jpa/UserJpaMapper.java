package se.jensen.yuki.springboot.user.infrastructure.jpa;

import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.vo.Email;
import se.jensen.yuki.springboot.user.domain.vo.HashedPassword;
import se.jensen.yuki.springboot.user.domain.vo.UserRole;
import se.jensen.yuki.springboot.user.domain.vo.Username;

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
                Username.of(user.getUsername()),
                Email.of(user.getEmail()),
                HashedPassword.of(user.getPassword()),
                UserRole.of(user.getRole()),
                user.getDisplayName(),
                user.getBio(),
                user.getAvatarUrl()
        );
    }
}
