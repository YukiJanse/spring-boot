package se.jensen.yuki.springboot.user.infrastructure.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.UserId;

import java.util.List;

/**
 * Implementation of UserRepository using JPA.
 */
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;
    private final UserJpaMapper userJpaMapper;

    /**
     * Finds a user by ID.
     *
     * @param id the ID of the user to find
     * @return the found user
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public User findById(UserId id) {
        return userJpaRepository.findById(id.getValue())
                .map(userJpaMapper::toDomain)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID=" + id));
    }

    /**
     * Saves a user. If the user has an ID, it updates the existing user; otherwise, it creates a new user.
     *
     * @param user the user to save
     * @throws UserNotFoundException if trying to update a user that does not exist
     */
    @Override
    public void save(User user) {
        UserJpaEntity entity;
        if (user.getId() == null) {
            entity = new UserJpaEntity();
            userJpaMapper.toEntity(user, entity);
        } else {
            entity = userJpaRepository.findById(user.getId().getValue())
                    .orElseThrow(() -> new UserNotFoundException("No user found with ID=" + user.getId()));
            userJpaMapper.toEntity(user, entity);
        }

        userJpaRepository.save(entity);
    }

    /**
     * Checks if a user exists by email.
     *
     * @param email the email to check
     * @return true if a user exists with the given email, false otherwise
     */
    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @Override
    public void deleteById(UserId id) {
        userJpaRepository.deleteById(id.getValue());
    }

    /**
     * Finds all users.
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
                .stream()
                .map(userJpaMapper::toDomain)
                .toList();
    }
}
