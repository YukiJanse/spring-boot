package se.jensen.yuki.springboot.user.domain.vo;

import java.util.Set;

/**
 * Value object representing a user's role in the system.
 * Valid roles are "USER" and "ADMIN".
 */
public final class UserRole {
    private final String value;
    // Define a set of valid roles for easy validation
    private static final Set VALID_ROLES = Set.of("USER", "ADMIN");

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the String value of the user role
     * @throws IllegalArgumentException if the value is null, blank, or not a valid role
     */
    private UserRole(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or blank");
        }

        String normalizedValue = value.trim().toUpperCase();

        if (!VALID_ROLES.contains(normalizedValue)) {
            throw new IllegalArgumentException("Invalid role: " + value);
        }

        this.value = normalizedValue;
    }

    /**
     * Factory method to create a new UserRole instance.
     *
     * @param value the String value of the user role
     * @return a new UserRole instance
     */
    public static UserRole of(String value) {
        return new UserRole(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole role)) return false;

        return value.equals(role.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
