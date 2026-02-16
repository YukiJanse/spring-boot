package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaRepository;

/**
 * Service for loading user data from the database.
 */
@Service
@RequiredArgsConstructor
public class UserLoadServiceImpl implements UserLoadService {
    private final UserJpaRepository userJpaRepository;

    /**
     * Loads a user by ID.
     *
     * @param id the ID of the user to load
     * @return the loaded user entity
     * @throws UserNotFoundException if no user is found with the given ID
     */
    @Override
    public UserJpaEntity loadById(Long id) {
        return userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found by id " + id));
    }

    /**
     * Loads a user by email.
     *
     * @param email the email of the user to load
     * @return the loaded user entity
     * @throws UserNotFoundException if no user is found with the given email
     */
    @Override
    public UserJpaEntity loadByEmail(String email) {
        return userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found by email " + email));
    }
}
