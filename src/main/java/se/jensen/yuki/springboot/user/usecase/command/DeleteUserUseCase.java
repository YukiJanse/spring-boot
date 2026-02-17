package se.jensen.yuki.springboot.user.usecase.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.UserId;

/**
 * Use case for deleting a user by ID.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    @Transactional
    public void execute(Long id) {
        log.debug("Starting to delete a user by ID={}", id);

        userRepository.deleteById(UserId.of(id));

        log.debug("Deleted a user successfully by ID={}", id);
    }
}
