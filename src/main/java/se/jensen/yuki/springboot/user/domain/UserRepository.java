package se.jensen.yuki.springboot.user.domain;

import java.util.List;

public interface UserRepository {
    User findById(Long id);

    void save(User user);

    boolean existsByEmail(String email);

    void deleteById(Long id);

    List<User> findAll();
}
