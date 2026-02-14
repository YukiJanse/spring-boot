package se.jensen.yuki.springboot.user.usecase.command;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.HashedPassword;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdatePasswordUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(Long id, @Valid UserUpdatePasswordRequest request) {
        log.debug("Starting to update a user with ID={}", id);

        User user = userRepository.findById(id);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new IllegalArgumentException("New password must be different from the current password");
        }

        HashedPassword newHashedPassword = HashedPassword.of(passwordEncoder.encode(request.newPassword()));

        user.changePassword(newHashedPassword);

        userRepository.save(user);

        log.debug("Updated a user successfully with ID={}", id);
    }
}
