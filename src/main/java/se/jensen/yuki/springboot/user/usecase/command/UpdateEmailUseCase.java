package se.jensen.yuki.springboot.user.usecase.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateEmailRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEmailUseCase {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public void execute(Long id, UserUpdateEmailRequest request) {
        log.debug("Starting to update a user with ID={}", id);

        UserJpaEntity user = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found"));

        // TODO: Add email validation logic here
        user.setEmail(request.email());

        log.debug("Updated a user successfully with ID={}", id);
    }
}
