package se.jensen.yuki.springboot.user.domain.vo;

import java.util.regex.Pattern;

public final class HashedPassword {
    private final String value;
    private final static int LENGTH = 60;
    private final static Pattern BCRYPT_PATTERN = Pattern.compile("^\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}$");

    public HashedPassword(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be null or blank");
        }

        if (value.length() != LENGTH) {
            throw new IllegalArgumentException("Invalid BCrypt hash length");
        }

        if (!BCRYPT_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Invalid password format");
        }

        this.value = value;
    }

    public static HashedPassword of(String value) {
        return new HashedPassword(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashedPassword password)) return false;

        return value.equals(password.value);
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
