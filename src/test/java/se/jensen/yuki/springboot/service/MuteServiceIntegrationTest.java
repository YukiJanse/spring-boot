package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.mute.MuteMutingResponse;
import se.jensen.yuki.springboot.dto.mute.MuteResponse;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.usecase.UserQueryService;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MuteServiceIntegrationTest {

    @Autowired
    private MuteService muteService;

    @Autowired
    private UserQueryService userQueryService;


    private UserJpaEntity user1;
    private UserJpaEntity user2;


    @BeforeEach
    void setUp() {

        user1 = userQueryService.save(
                UserJpaEntity.builder()
                        .username("user1")
                        .email("user1@test.com")
                        .password("pass")
                        .displayName("User1")
                        .bio("bio")
                        .role("USER")
                        .build()
        );

        user2 = userQueryService.save(
                UserJpaEntity.builder()
                        .username("user2")
                        .email("user2@test.com")
                        .password("pass")
                        .displayName("User2")
                        .bio("bio")
                        .role("USER")
                        .build()
        );
    }


    // -----------------------
    // Mute
    // -----------------------

    @Test
    void muteUser_shouldCreateMute() {

        MuteResponse res =
                muteService.muteUser(user1.getId(), user2.getId());

        assertThat(res).isNotNull();
        assertThat(res.mutedUserId()).isEqualTo(user2.getId());
    }


    @Test
    void muteUser_shouldNotDuplicate() {

        muteService.muteUser(user1.getId(), user2.getId());

        MuteResponse res =
                muteService.muteUser(user1.getId(), user2.getId());

        assertThat(res).isNotNull();
    }


    @Test
    void unmuteUser_shouldRemoveMute() {

        muteService.muteUser(user1.getId(), user2.getId());

        boolean result =
                muteService.unmuteUser(user1.getId(), user2.getId());

        assertThat(result).isTrue();

        // 再度解除は false
        boolean second =
                muteService.unmuteUser(user1.getId(), user2.getId());

        assertThat(second).isFalse();
    }


    // -----------------------
    // List
    // -----------------------

    @Test
    void getMutings_shouldReturnList() {

        muteService.muteUser(user1.getId(), user2.getId());

        Slice<MuteMutingResponse> slice =
                muteService.getMutings(
                        user1.getId(),
                        PageRequest.of(0, 10)
                );

        assertThat(slice).isNotNull();
        assertThat(slice.getContent()).hasSize(1);

        MuteMutingResponse res =
                slice.getContent().getFirst();

        assertThat(res.mutedUserId()).isEqualTo(user2.getId());
    }
}
