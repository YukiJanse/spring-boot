package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeleteUserUseCase {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void execute(Long id) {
        log.debug("Starting to delete a user by ID={}", id);

        userJpaRepository.deleteById(id);

        log.debug("Deleted a user successfully by ID={}", id);
    }
}
