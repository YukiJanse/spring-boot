package se.jensen.yuki.springboot.user.domain.vo;

/**
 * Value object representing a username in the user domain.
 * <p>
 * This class is immutable and validates the username according to specific rules:
 * - The username cannot be null or blank.
 * - The username must be between 3 and 20 characters long.
 */
public final class Username {
    private final String value;
    private static final int MIN_LENGTH = 3;
    private static final int MAX_LENGTH = 20;

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the username string
     * @throws IllegalArgumentException if the username is null, blank, or does not meet length requirements
     */
    private Username(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }

        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }

        this.value = value;
    }

    /**
     * Factory method to create a new Username instance.
     *
     * @param value the username string
     * @return a new Username instance
     */
    public static Username of(String value) {
        return new Username(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Username username)) return false;

        return value.equals(username.value);
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
