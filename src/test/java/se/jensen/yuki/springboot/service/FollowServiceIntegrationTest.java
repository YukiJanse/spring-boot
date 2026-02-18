package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.follow.FollowResponse;
import se.jensen.yuki.springboot.repository.FollowRepository;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FollowServiceIntegrationTest {
    @Autowired
    private FollowService followService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private FollowRepository followRepository;


    private UserJpaEntity userA;
    private UserJpaEntity userB;


    @BeforeEach
    void setup() {

        followRepository.deleteAll();
        userJpaRepository.deleteAll();

        userA = userJpaRepository.save(
                UserJpaEntity.builder()
                        .username("userA")
                        .email("a@test.com")
                        .password("pw")
                        .role("USER")
                        .displayName("A")
                        .bio("bio")
                        .build()
        );

        userB = userJpaRepository.save(
                UserJpaEntity.builder()
                        .username("userB")
                        .email("b@test.com")
                        .password("pw")
                        .role("USER")
                        .displayName("B")
                        .bio("bio")
                        .build()
        );
    }


    // ---------- follow ----------

    @Test
    void followUser_whenNotFollowed_createsRelation() {

        FollowResponse response =
                followService.followUser(
                        userA.getId(),
                        userB.getId()
                );

        assertThat(response).isNotNull();

        assertThat(
                followRepository.existsByFollowerIdAndFollowedId(
                        userA.getId(),
                        userB.getId()
                )
        ).isTrue();
    }


    @Test
    void followUser_whenAlreadyFollowed_doesNotDuplicate() {

        // manually create the relationship to simulate the "already followed" state
        followRepository.createFollowRelationship(
                userA.getId(),
                userB.getId()
        );

        long countBefore = followRepository.count();

        FollowResponse response =
                followService.followUser(
                        userA.getId(),
                        userB.getId()
                );

        long countAfter = followRepository.count();

        assertThat(response).isNotNull();
        assertThat(countAfter).isEqualTo(countBefore);
    }


    // ---------- unfollow ----------

    @Test
    void unfollowUser_whenExists_deletes() {

        followRepository.createFollowRelationship(
                userA.getId(),
                userB.getId()
        );

        boolean result =
                followService.unfollowUser(
                        userA.getId(),
                        userB.getId()
                );

        assertThat(result).isTrue();

        assertThat(
                followRepository.existsByFollowerIdAndFollowedId(
                        userA.getId(),
                        userB.getId()
                )
        ).isFalse();
    }


    @Test
    void unfollowUser_whenNotExists_returnsFalse() {

        boolean result =
                followService.unfollowUser(
                        userA.getId(),
                        userB.getId()
                );

        assertThat(result).isFalse();
    }
}
