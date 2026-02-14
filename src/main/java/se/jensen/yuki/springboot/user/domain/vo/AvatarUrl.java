package se.jensen.yuki.springboot.user.domain.vo;

public final class AvatarUrl {
    private final String value;

    private AvatarUrl(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Avatar URL cannot be null or blank");
        }

        this.value = value;
    }

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
