package se.jensen.yuki.springboot.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.post.PostCreateRequest;
import se.jensen.yuki.springboot.dto.post.PostFeedResponse;
import se.jensen.yuki.springboot.exception.PostNotFoundException;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.model.User;
import se.jensen.yuki.springboot.repository.UserRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setup() {

        testUser = User.builder()
                .username("testuser")
                .email("test@test.com")
                .password("password")
                .role("USER")
                .displayName("Test User")
                .bio("bio")
                .avatarUrl(null)
                .build();

        testUser = userRepository.save(testUser);
    }

    /**
     * Pattern1: add post successfully
     */
    @Test
    void addPost_success() {

        PostCreateRequest request =
                new PostCreateRequest("Hello Integration Test");
        PostFeedResponse response =
                postService.addPost(testUser.getId(), request);

        assertThat(response).isNotNull();
        assertThat(response.content()).isEqualTo("Hello Integration Test");
        assertThat(response.authorUserId()).isEqualTo(testUser.getId());
    }

    /**
     * Pattern2: user not found when adding a post
     */
    @Test
    void addPost_userNotFound() {

        PostCreateRequest request =
                new PostCreateRequest("Fail Test");

        assertThatThrownBy(() ->
                postService.addPost(9999L, request)
        ).isInstanceOf(UserNotFoundException.class);
    }

    /**
     * Pattern3: post not found when getting a post by id
     */
    @Test
    void getPost_notFound() {

        assertThatThrownBy(() ->
                postService.getPostById(999L)
        ).isInstanceOf(PostNotFoundException.class);
    }
}

