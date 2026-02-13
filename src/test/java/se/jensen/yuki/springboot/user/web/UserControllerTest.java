package se.jensen.yuki.springboot.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import se.jensen.yuki.springboot.testutil.TestUsers;
import se.jensen.yuki.springboot.user.usecase.command.DeleteUserUseCase;
import se.jensen.yuki.springboot.user.usecase.command.UpdateEmailUseCase;
import se.jensen.yuki.springboot.user.usecase.command.UpdatePasswordUseCase;
import se.jensen.yuki.springboot.user.usecase.command.UpdateUserProfileUseCase;
import se.jensen.yuki.springboot.user.usecase.query.UserQueryService;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateEmailRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdatePasswordRequest;
import se.jensen.yuki.springboot.user.web.dto.UserUpdateProfileRequest;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    UpdatePasswordUseCase updatePasswordUseCase;
    @MockitoBean
    UpdateEmailUseCase updateEmailUseCase;
    @MockitoBean
    UpdateUserProfileUseCase updateUserProfileUseCase;
    @MockitoBean
    DeleteUserUseCase deleteUserUseCase;
    @MockitoBean
    UserQueryService userQueryService;

    /* -------------------------
       GET /users/me
       ------------------------- */
    @Test
    void getMyProfile_shouldReturnProfile() throws Exception {

        UserProfileResponse response =
                new UserProfileResponse(1L, "user", "user@test.com", "User", "user", "Bio", "avatar.png");

        when(userQueryService.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/users/me")
                        .with(user(TestUsers.user())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("user@test.com"));
    }

    /* -------------------------
       PUT /users/me/password
       ------------------------- */
    @Test
    void updatePassword_shouldReturnNoContent() throws Exception {

        UserUpdatePasswordRequest request =
                new UserUpdatePasswordRequest("newPass123", "currentPass123");

        mockMvc.perform(put("/users/me/password")
                        .with(user(TestUsers.user()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updatePasswordUseCase)
                .execute(eq(1L), any(UserUpdatePasswordRequest.class));
    }

    /* -------------------------
       PUT /users/me/email
       ------------------------- */
    @Test
    void updateEmail_shouldReturnNoContent() throws Exception {

        UserUpdateEmailRequest request =
                new UserUpdateEmailRequest("new@mail.com", "currentPass123");

        mockMvc.perform(put("/users/me/email")
                        .with(user(TestUsers.user()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(updateEmailUseCase)
                .execute(eq(1L), any(UserUpdateEmailRequest.class));
    }

    /* -------------------------
       PUT /users/me/profile
       ------------------------- */
    @Test
    void updateProfile_shouldReturnUpdatedProfile() throws Exception {

        UserUpdateProfileRequest request =
                new UserUpdateProfileRequest("NewName", "NewBio", "/newAvatar.png");

        UserProfileResponse response =
                new UserProfileResponse(1L, "user", "user@test.com", "User", "NewName", "NewBio", "/newAvatar.png");

        when(updateUserProfileUseCase.execute(eq(1L), any()))
                .thenReturn(response);

        mockMvc.perform(put("/users/me/profile")
                        .with(user(TestUsers.user()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.displayName").value("NewName"));
    }

    /* -------------------------
       DELETE /users/{id}
       ------------------------- */
    @Test
    void deleteUser_shouldReturnNoContent() throws Exception {

        mockMvc.perform(delete("/users/1")
                        .with(user(TestUsers.user())))
                .andExpect(status().isNoContent());

        verify(deleteUserUseCase).execute(1L);
    }
}

