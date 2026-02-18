package se.jensen.yuki.springboot.user.usecase.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.domain.vo.UserId;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    DeleteUserUseCase useCase;

    @Test
    void execute_ShouldDeleteUserSuccessfully() {
        // Given
        Long userId = 1L;

        // Then
        assertDoesNotThrow(() -> useCase.execute(userId));
        verify(userRepository).deleteById(UserId.of(userId));
    }

    @Test
    void execute_ShouldHandleNonExistingUser() {
        // Given
        Long nonExistingUserId = 999L;
        UserId nonExistingUserIdVo = UserId.of(nonExistingUserId);

        doThrow(new RuntimeException("DB failure"))
                .when(userRepository).deleteById(nonExistingUserIdVo);

        assertThrows(RuntimeException.class,
                () -> useCase.execute(nonExistingUserId));
    }
}