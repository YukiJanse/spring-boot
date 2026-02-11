package se.jensen.yuki.springboot.user.infrastructure.jpa;

import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.user.domain.User;

@Component
public class UserJpaMapper {
    public UserJpaEntity toEntity(User user) {
        return UserJpaEntity.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .displayName(user.getDisplayName())
                .bio(user.getBio())
                .avatarUrl(user.getAvatarUrl())
                .build();
    }

    public User toDomain(UserJpaEntity user) {
        return new User(
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
