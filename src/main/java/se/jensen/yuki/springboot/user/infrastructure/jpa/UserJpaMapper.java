package se.jensen.yuki.springboot.user.infrastructure.jpa;

import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.vo.*;

@Component
public class UserJpaMapper {
    public void toEntity(User user, UserJpaEntity entity) {
        entity.setUsername(user.getUsername().getValue());
        entity.setEmail(user.getEmail().getValue());
        entity.setPassword(user.getPassword().getValue());
        entity.setRole(user.getRole().getValue());
        entity.setDisplayName(user.getDisplayName().getValue());
        entity.setBio(user.getBio().getValue());
        entity.setAvatarUrl(user.getAvatarUrl().getValue());
    }

    public User toDomain(UserJpaEntity user) {
        return User.reconstruct(
                UserId.of(user.getId()),
                Username.of(user.getUsername()),
                Email.of(user.getEmail()),
                HashedPassword.of(user.getPassword()),
                UserRole.of(user.getRole()),
                DisplayName.of(user.getDisplayName()),
                Bio.of(user.getBio()),
                AvatarUrl.of(user.getAvatarUrl())
        );
    }
}
