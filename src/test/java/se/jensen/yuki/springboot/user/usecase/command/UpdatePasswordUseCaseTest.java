package se.jensen.yuki.springboot.user.usecase.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdatePasswordUseCaseTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UpdatePasswordUseCase useCase;

    @Test
    void execute_shouldUpdatePassword_whenValidRequest() {
        Long userId = 1L;

        UserUpdatePasswordRequest request =
                new UserUpdatePasswordRequest("new-password", "current-password");

        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(user);
        when(user.getPassword()).thenReturn("encoded-password");

        when(passwordEncoder.matches("current-password", "encoded-password"))
                .thenReturn(true);

        when(passwordEncoder.matches("new-password", "encoded-password"))
                .thenReturn(false);

        when(passwordEncoder.encode("new-password"))
                .thenReturn("new-encoded-password");

        useCase.execute(userId, request);

        verify(user).changePassword("new-encoded-password");
        verify(userRepository).save(user);
    }

    @Test
    void execute_shouldThrowException_whenCurrentPasswordIncorrect() {
        Long userId = 1L;

        UserUpdatePasswordRequest request =
                new UserUpdatePasswordRequest("new-password", "wrong-password");

        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(user);
        when(user.getPassword()).thenReturn("encoded-password");

        when(passwordEncoder.matches("wrong-password", "encoded-password"))
                .thenReturn(false);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(userId, request));

        verify(user, never()).changePassword(any());
        verify(userRepository, never()).save(any());
    }

    @Test
    void execute_shouldThrowException_whenNewPasswordSameAsCurrent() {
        Long userId = 1L;

        UserUpdatePasswordRequest request =
                new UserUpdatePasswordRequest("same-password", "current-password");

        User user = mock(User.class);

        when(userRepository.findById(userId)).thenReturn(user);
        when(user.getPassword()).thenReturn("encoded-password");

        when(passwordEncoder.matches("current-password", "encoded-password"))
                .thenReturn(true);

        when(passwordEncoder.matches("same-password", "encoded-password"))
                .thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(userId, request));

        verify(user, never()).changePassword(any());
        verify(userRepository, never()).save(any());
    }
}
