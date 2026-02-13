package se.jensen.yuki.springboot.user.usecase.command;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.UserRepository;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateProfileRequest;
import se.jensen.yuki.springboot.user.web.mapper.UserResponseMapper;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateUserProfileUseCaseTest {

    @Mock
    UserRepository userRepository;

    @Mock
    UserResponseMapper userMapper;

    @InjectMocks
    UpdateUserProfileUseCase useCase;

    @Test
    void execute_shouldUpdateProfile_andReturnResponse() {
        Long userId = 1L;

        UserUpdateProfileRequest request =
                new UserUpdateProfileRequest("NewName", "NewBio", "/avatar.png");

        User user = mock(User.class);
        UserProfileResponse response = mock(UserProfileResponse.class);

        when(userRepository.findById(userId)).thenReturn(user);
        when(userMapper.toResponse(user)).thenReturn(response);

        UserProfileResponse result = useCase.execute(userId, request);

        verify(user).updateProfile("NewName", "NewBio", "/avatar.png");
        verify(userRepository).save(user);
        verify(userMapper).toResponse(user);

        assertSame(response, result);
    }
}
