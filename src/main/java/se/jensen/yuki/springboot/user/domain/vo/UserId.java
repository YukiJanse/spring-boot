package se.jensen.yuki.springboot.user.domain.vo;

/**
 * Value object representing a User ID.
 */
public final class UserId {
    private final Long value;

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the Long value of the User ID
     * @throws IllegalArgumentException if the value is null or not a positive number
     */
    private UserId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number.");
        }

        this.value = value;
    }

    /**
     * Factory method to create a new UserId instance.
     *
     * @param value the Long value of the User ID
     * @return a new UserId instance
     */
    public static UserId of(Long value) {
        return new UserId(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserId userId)) return false;

        return value.equals(userId.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
