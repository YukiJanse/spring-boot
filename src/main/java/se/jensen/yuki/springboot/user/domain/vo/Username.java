package se.jensen.yuki.springboot.user.domain.vo;

public final class Username {
    private final String value;

    private Username(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }

        if (value.length() < 3 || value.length() > 20) {
            throw new IllegalArgumentException("Username must be between 3 and 20 characters");
        }

        this.value = value;
    }

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
