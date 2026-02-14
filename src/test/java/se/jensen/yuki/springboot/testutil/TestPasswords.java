package se.jensen.yuki.springboot.testutil;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class TestPasswords {
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String hashed(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
