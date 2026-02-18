package se.jensen.yuki.springboot.testutil;

import se.jensen.yuki.springboot.model.SecurityUser;

public class TestUsers {

    public static SecurityUser user() {
        return new SecurityUser(1L, "user@test.com", "USER");
    }

    public static SecurityUser admin() {
        return new SecurityUser(99L, "admin@test.com", "ADMIN");
    }
}
