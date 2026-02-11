package se.jensen.yuki.springboot.user.usecase.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.domain.UserRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserRepository userRepository;

    @Transactional
    public void execute(Long id) {
        log.debug("Starting to delete a user by ID={}", id);

        userRepository.deleteById(id);

        log.debug("Deleted a user successfully by ID={}", id);
    }
}
