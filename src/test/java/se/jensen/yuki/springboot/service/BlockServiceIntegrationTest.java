package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.block.BlockBlockingResponse;
import se.jensen.yuki.springboot.dto.block.BlockResponse;
import se.jensen.yuki.springboot.repository.BlockRepository;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class BlockServiceIntegrationTest {

    @Autowired
    private BlockService blockService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlockRepository blockRepository;

    private UserJpaEntity userA;
    private UserJpaEntity userB;

    @BeforeEach
    void setUp() {

        userA = userRepository.save(
                UserJpaEntity.builder()
                        .username("userA")
                        .email("a@test.com")
                        .password("pass")
                        .displayName("User A")
                        .bio("bioA")
                        .role("USER")
                        .build()
        );

        userB = userRepository.save(
                UserJpaEntity.builder()
                        .username("userB")
                        .email("b@test.com")
                        .password("pass")
                        .displayName("User B")
                        .bio("bioB")
                        .role("USER")
                        .build()
        );
    }

    // -------------------------------
    // blockUser
    // -------------------------------

    @Test
    void blockUser_shouldCreateNewBlock() {

        BlockResponse response =
                blockService.blockUser(userA.getId(), userB.getId());

        assertThat(response).isNotNull();
        assertThat(response.blockedUserId()).isEqualTo(userB.getId());

        boolean exists =
                blockRepository.existsByBlockingIdAndBlockedId(
                        userA.getId(), userB.getId());

        assertThat(exists).isTrue();
    }


    @Test
    void blockUser_shouldNotDuplicateBlock() {

        // first
        blockService.blockUser(userA.getId(), userB.getId());

        // second
        BlockResponse response =
                blockService.blockUser(userA.getId(), userB.getId());

        assertThat(response).isNotNull();

        long count =
                blockRepository.countByBlockingIdAndBlockedId(
                        userA.getId(), userB.getId());

        assertThat(count).isEqualTo(1);
    }


    // -------------------------------
    // unblockUser
    // -------------------------------

    @Test
    void unblockUser_shouldRemoveBlock() {

        blockService.blockUser(userA.getId(), userB.getId());

        boolean result =
                blockService.unblockUser(userA.getId(), userB.getId());

        assertThat(result).isTrue();

        boolean exists =
                blockRepository.existsByBlockingIdAndBlockedId(
                        userA.getId(), userB.getId());

        assertThat(exists).isFalse();
    }


    @Test
    void unblockUser_shouldReturnFalseWhenNotExists() {

        boolean result =
                blockService.unblockUser(userA.getId(), userB.getId());

        assertThat(result).isFalse();
    }


    // -------------------------------
    // getBlockings
    // -------------------------------

    @Test
    void getBlockings_shouldReturnBlockedUsers() {

        blockService.blockUser(userA.getId(), userB.getId());

        Slice<BlockBlockingResponse> slice =
                blockService.getBlockings(
                        userA.getId(),
                        PageRequest.of(0, 10)
                );

        assertThat(slice).isNotNull();
        assertThat(slice.getContent()).hasSize(1);

        BlockBlockingResponse res =
                slice.getContent().get(0);

        assertThat(res.blockedUserId()).isEqualTo(userB.getId());
    }
}
