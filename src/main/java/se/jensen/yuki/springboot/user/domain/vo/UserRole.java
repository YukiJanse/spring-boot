package se.jensen.yuki.springboot.user.domain.vo;

import java.util.Set;

public final class UserRole {
    private final String value;
    private static final Set VALID_ROLES = Set.of("USER", "ADMIN");

    public UserRole(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Role cannot be null or blank");
        }

        String normalizedValue = value.trim().toUpperCase();

        if (!VALID_ROLES.contains(normalizedValue)) {
            throw new IllegalArgumentException("Invalid role: " + value);
        }

        this.value = normalizedValue;
    }

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
