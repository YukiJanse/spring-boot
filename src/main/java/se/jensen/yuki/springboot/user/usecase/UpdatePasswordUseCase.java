package se.jensen.yuki.springboot.user.usecase;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaRepository;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(Long id, @Valid UserUpdatePasswordRequest request) {
        log.debug("Starting to update a user with ID={}", id);

        UserJpaEntity user = userJpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found"));

        user.setPassword(passwordEncoder.encode(request.newPassword()));

        log.debug("Updated a user successfully with ID={}", id);
    }
}
