package se.jensen.yuki.springboot.user.domain.vo;

/**
 * Value object representing a user's avatar URL.
 */
public final class AvatarUrl {
    private final String value;

    /**
     * Private constructor to enforce the use of the factory method.
     *
     * @param value the avatar URL string
     * @throws IllegalArgumentException if the value is null or blank
     */
    private AvatarUrl(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Avatar URL cannot be null or blank");
        }

        this.value = value;
    }

    /**
     * Factory method to create an AvatarUrl instance.
     *
     * @param value the avatar URL string
     * @return a new AvatarUrl instance
     * @throws IllegalArgumentException if the value is null or blank
     */
    public static AvatarUrl of(String value) {
        return new AvatarUrl(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvatarUrl avatarUrl)) return false;

        return value.equals(avatarUrl.value);
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
