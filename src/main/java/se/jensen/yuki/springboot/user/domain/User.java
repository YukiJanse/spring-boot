package se.jensen.yuki.springboot.user.domain;

import lombok.Getter;
import se.jensen.yuki.springboot.user.domain.vo.*;

@Getter
public class User {
    private final UserId id;
    private final Username username;
    private Email email;
    private HashedPassword password;
    private final UserRole role;
    private DisplayName displayName;
    private Bio bio;
    private AvatarUrl avatarUrl;

    private User(UserId id, Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.displayName = displayName;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public static User create(Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        return new User(null, username, email, password, role, displayName, bio, avatarUrl);
    }

    public static User reconstruct(UserId id, Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        return new User(id, username, email, password, role, displayName, bio, avatarUrl);
    }

    public void changeEmail(Email newEmail) {
        if (this.email.equals(newEmail)) {
            throw new IllegalArgumentException("New email must be different from the current email.");
        }
        this.email = newEmail;
    }

    public void changePassword(HashedPassword newHashedPassword) {
        // TODO: add validation for the new hashed password if needed
        this.password = newHashedPassword;
    }

    public void updateProfile(DisplayName newDisplayName, Bio newBio, AvatarUrl newAvatarUrl) {
        // TODO: add validation for display name, bio, and avatar URL if needed
        this.displayName = newDisplayName;
        this.bio = newBio;
        this.avatarUrl = newAvatarUrl;
    }
}
