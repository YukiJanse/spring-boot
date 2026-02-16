package se.jensen.yuki.springboot.user.domain.vo;

import java.util.regex.Pattern;

public final class DisplayName {
    private final String value;
    private static final int MAX_LENGTH = 30;
    private static final int MIN_LENGTH = 3;
    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[\\p{L}\\p{N}][\\p{L}\\p{N} _.-]{2,29}$");

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
