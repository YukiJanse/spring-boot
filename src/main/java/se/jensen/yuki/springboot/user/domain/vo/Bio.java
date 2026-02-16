package se.jensen.yuki.springboot.user.domain.vo;

import java.util.regex.Pattern;

/**
 * Value object representing a user's bio.
 * <p>
 * The bio must be between 0 and 200 characters and can only contain letters, numbers, spaces, and basic punctuation.
 */
public final class Bio {
    private final String value;
    // Maximum length for a bio
    private static final int MAX_LENGTH = 200;
    // Regex pattern to allow letters, numbers, spaces, and basic punctuation
    private static final Pattern BIO_PATTERN = Pattern.compile("^[\\p{L}\\p{N} ,.?!'\"\\-()]*$");

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the bio string
     * @throws IllegalArgumentException if the bio is null, exceeds the maximum length, or contains invalid characters
     */
    private Bio(String value) {
        if (value == null || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Bio must be between 0 and " + MAX_LENGTH + " characters");
        }

        if (!BIO_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Bio contains invalid characters");
        }

        this.value = value;
    }

    /**
     * Factory method to create a new Bio instance.
     *
     * @param value the bio string
     * @return a new Bio instance
     */
    public static Bio of(String value) {
        return new Bio(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bio bio)) return false;

        return value.equals(bio.value);
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
