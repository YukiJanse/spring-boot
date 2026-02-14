package se.jensen.yuki.springboot.user.domain;

import se.jensen.yuki.springboot.user.domain.vo.*;

public class User {
    private final Long id;
    private Username username;
    private Email email;
    private HashedPassword password;
    private UserRole role;
    private DisplayName displayName;
    private Bio bio;
    private AvatarUrl avatarUrl;

    public User(Long id, Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.displayName = displayName;
        this.bio = bio;
        this.avatarUrl = avatarUrl;
    }

    public static User reconstruct(Long id, Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        return new User(id, username, email, password, role, displayName, bio, avatarUrl);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username.getValue();
    }

    public String getEmail() {
        return email.getValue();
    }

    public String getPassword() {
        return password.getValue();
    }

    public String getRole() {
        return role.getValue();
    }

    public String getDisplayName() {
        return displayName.getValue();
    }

    public String getBio() {
        return bio.getValue();
    }

    public String getAvatarUrl() {
        return avatarUrl.getValue();
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
