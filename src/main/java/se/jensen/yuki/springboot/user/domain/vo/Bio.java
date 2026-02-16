package se.jensen.yuki.springboot.user.domain.vo;

import java.util.regex.Pattern;

public final class Bio {
    private final String value;
    private static final int MAX_LENGTH = 200;
    private static final Pattern BIO_PATTERN = Pattern.compile("^[\\p{L}\\p{N} ,.?!'\"\\-()]*$");

    private Bio(String value) {
        if (value == null || value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Bio must be between 0 and " + MAX_LENGTH + " characters");
        }

        if (!BIO_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Bio contains invalid characters");
        }

        this.value = value;
    }

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
