package se.jensen.yuki.springboot.user.usecase.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.Email;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateEmailRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateEmailUseCaseTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UpdateEmailUseCase useCase;

    @Test
    void execute_shouldUpdateEmail_whenPasswordMatches() {
        Long userId = 1L;

        UserUpdateEmailRequest request =
                new UserUpdateEmailRequest("new@mail.com", "correct-password");

        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(user);
        when(user.getPassword()).thenReturn("encoded-password");
        when(passwordEncoder.matches("correct-password", "encoded-password"))
                .thenReturn(true);

        useCase.execute(userId, request);

        verify(user).changeEmail(any(Email.class));
        verify(userRepository).save(user);
    }

    @Test
    void execute_shouldThrowException_whenPasswordDoesNotMatch() {
        Long userId = 1L;

        UserUpdateEmailRequest request =
                new UserUpdateEmailRequest("new@mail.com", "wrong-password");

        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(user);
        when(user.getPassword()).thenReturn("encoded-password");
        when(passwordEncoder.matches("wrong-password", "encoded-password"))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(userId, request));

        verify(user, never()).changeEmail(any());
        verify(userRepository, never()).save(any());
    }
}