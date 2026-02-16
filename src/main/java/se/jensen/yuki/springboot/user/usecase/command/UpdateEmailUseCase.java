package se.jensen.yuki.springboot.user.usecase.command;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.Email;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateEmailRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateEmailUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void execute(Long id, UserUpdateEmailRequest request) {
        log.debug("Starting to update a user with ID={}", id);

        User user = userRepository.findById(id);

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword().getValue())) {
            throw new IllegalArgumentException("Current password is incorrect");
        }

        Email newEmail = Email.of(request.email());

        user.changeEmail(newEmail);

        userRepository.save(user);

        log.debug("Updated a user successfully with ID={}", id);
    }
}
