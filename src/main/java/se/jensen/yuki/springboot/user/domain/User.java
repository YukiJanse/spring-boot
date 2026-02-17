package se.jensen.yuki.springboot.user.domain;

import lombok.Getter;
import se.jensen.yuki.springboot.user.domain.vo.*;

import java.util.Objects;

/**
 * Represents a user in the system with properties such as username, email, password, role, display name, bio, and avatar URL.
 * Provides methods for creating and reconstructing users, as well as updating user information.
 */
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

    /**
     * Private constructor to enforce the use of factory methods for creating and reconstructing users.
     *
     * @param id          the unique identifier of the user, can be null for new users
     * @param username    the username of the user, must not be null
     * @param email       the email address of the user, must not be null
     * @param password    the hashed password of the user, must not be null
     * @param role        the role of the user, must not be null
     * @param displayName the display name of the user, must not be null
     * @param bio         the bio of the user, must not be null
     * @param avatarUrl   the avatar URL of the user, must not be null
     */
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

    /**
     * Factory method to create a new user with the specified properties. The ID is set to null for new users.
     *
     * @param username    the username of the user, must not be null
     * @param email       the email address of the user, must not be null
     * @param password    the hashed password of the user, must not be null
     * @param role        the role of the user, must not be null
     * @param displayName the display name of the user, must not be null
     * @param bio         the bio of the user, must not be null
     * @param avatarUrl   the avatar URL of the user, must not be null
     * @return a new User instance with the specified properties and a null ID
     */
    public static User create(Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        return new User(null, username, email, password, role, displayName, bio, avatarUrl);
    }

    /**
     * Factory method to reconstruct an existing user with the specified properties, including the ID.
     *
     * @param id          the unique identifier of the user, must not be null
     * @param username    the username of the user, must not be null
     * @param email       the email address of the user, must not be null
     * @param password    the hashed password of the user, must not be null
     * @param role        the role of the user, must not be null
     * @param displayName the display name of the user, must not be null
     * @param bio         the bio of the user, must not be null
     * @param avatarUrl   the avatar URL of the user, must not be null
     * @return a User instance with the specified properties and ID
     */
    public static User reconstruct(UserId id, Username username, Email email, HashedPassword password, UserRole role, DisplayName displayName, Bio bio, AvatarUrl avatarUrl) {
        Objects.requireNonNull(id, "User ID cannot be null for reconstruction");
        return new User(id, username, email, password, role, displayName, bio, avatarUrl);
    }

    /**
     * Updates the user's email address. The new email must not be null and must be different from the current email.
     *
     * @param newEmail the new email address to set, must not be null and must be different from the current email
     * @throws IllegalArgumentException if the new email is null or the same as the current email
     */
    public void changeEmail(Email newEmail) {
        Objects.requireNonNull(newEmail, "New email cannot be null");

        if (this.email.equals(newEmail)) {
            throw new IllegalArgumentException("New email must be different from the current email.");
        }

        this.email = newEmail;
    }

    /**
     * Updates the user's password. The new password must not be null and must be different from the current password.
     *
     * @param newHashedPassword the new hashed password to set, must not be null and must be different from the current password
     * @throws IllegalArgumentException if the new password is null or the same as the current password
     */
    public void changePassword(HashedPassword newHashedPassword) {
        Objects.requireNonNull(newHashedPassword, "New password cannot be null");

        if (this.password.equals(newHashedPassword)) {
            throw new IllegalArgumentException("New password must be different from the current password.");
        }

        this.password = newHashedPassword;
    }

    /**
     * Updates the user's profile information, including display name, bio, and avatar URL. All new values must not be null.
     *
     * @param newDisplayName the new display name to set, must not be null
     * @param newBio         the new bio to set, must not be null
     * @param newAvatarUrl   the new avatar URL to set, must not be null
     * @throws IllegalArgumentException if any of the new values are null
     */
    public void updateProfile(DisplayName newDisplayName, Bio newBio, AvatarUrl newAvatarUrl) {
        Objects.requireNonNull(newDisplayName, "Display name cannot be null");
        Objects.requireNonNull(newBio, "Bio cannot be null");
        Objects.requireNonNull(newAvatarUrl, "Avatar URL cannot be null");

        this.displayName = newDisplayName;
        this.bio = newBio;
        this.avatarUrl = newAvatarUrl;
    }
}
