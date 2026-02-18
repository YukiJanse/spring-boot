package se.jensen.yuki.springboot.user.domain.vo;

import java.util.regex.Pattern;

/**
 * Value object representing a user's display name.
 * <p>
 * The display name must be between 3 and 30 characters long and can contain letters, numbers, spaces, underscores, hyphens, and periods.
 * It must start with a letter or number and cannot contain consecutive special characters.
 */
public final class DisplayName {
    private final String value;
    // Maximum and minimum length for a display name
    private static final int MAX_LENGTH = 30;
    private static final int MIN_LENGTH = 3;
    // Regex pattern to allow letters, numbers, spaces, underscores, hyphens, and periods, with specific rules for starting character and consecutive special characters
    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[\\p{L}\\p{N}][\\p{L}\\p{N} _.-]{2,29}$");

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the display name string
     * @throws IllegalArgumentException if the display name is null, empty, does not meet length requirements, or contains invalid characters
     */
    private DisplayName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Display name cannot be null or empty");
        }

        if (value.length() < 3 || value.length() > 30) {
            throw new IllegalArgumentException("Display name must be between 3 and 30 characters");
        }

        if (!VALID_CHARACTERS.matcher(value).matches()) {
            throw new IllegalArgumentException("Display name contains invalid characters");
        }

        this.value = value;
    }

    /**
     * Factory method to create a new DisplayName instance.
     *
     * @param value the display name string
     * @return a new DisplayName instance
     */
    public static DisplayName of(String value) {
        return new DisplayName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DisplayName that)) return false;

        return value.equals(that.value);
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
