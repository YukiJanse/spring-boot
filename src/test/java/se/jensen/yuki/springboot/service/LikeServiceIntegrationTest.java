package se.jensen.yuki.springboot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.like.CommentLikeResponse;
import se.jensen.yuki.springboot.dto.like.LikeResponse;
import se.jensen.yuki.springboot.dto.like.PostLikeResponse;
import se.jensen.yuki.springboot.model.Comment;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.repository.CommentLikeRepository;
import se.jensen.yuki.springboot.repository.CommentRepository;
import se.jensen.yuki.springboot.repository.PostLikeRepository;
import se.jensen.yuki.springboot.repository.PostRepository;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LikeServiceIntegrationTest {

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

    private UserJpaEntity user;
    private Post post;
    private Comment comment;

    @BeforeEach
    void setUp() {

        user = userJpaRepository.save(
                UserJpaEntity.builder()
                        .username("user1")
                        .email("user1@test.com")
                        .password("pass")
                        .displayName("User1")
                        .bio("bio")
                        .role("USER")
                        .build()
        );

        post = postRepository.save(
                Post.builder()
                        .content("test post")
                        .user(user)
                        .build()
        );

        comment = commentRepository.save(
                Comment.builder()
                        .content("test comment")
                        .author(user)
                        .post(post)
                        .build()
        );
    }

    // -----------------------
    // Post Like
    // -----------------------

    @Test
    void likePost_shouldCreateLike() {

        LikeResponse res =
                likeService.likePost(post.getId(), user.getId());

        assertThat(res.liked()).isTrue();
        assertThat(res.likeCount()).isEqualTo(1);

        assertThat(
                postLikeRepository.findByPostIdAndUserId(
                        post.getId(), user.getId()
                )
        ).isPresent();
    }


    @Test
    void likePost_shouldNotDuplicate() {

        likeService.likePost(post.getId(), user.getId());

        LikeResponse res =
                likeService.likePost(post.getId(), user.getId());

        assertThat(res.likeCount()).isEqualTo(1);
    }


    @Test
    void unlikePost_shouldRemoveLike() {

        likeService.likePost(post.getId(), user.getId());

        LikeResponse res =
                likeService.unlikePost(post.getId(), user.getId());

        assertThat(res.liked()).isFalse();
        assertThat(res.likeCount()).isEqualTo(0);

        assertThat(
                postLikeRepository.findByPostIdAndUserId(
                        post.getId(), user.getId()
                )
        ).isEmpty();
    }


    // -----------------------
    // Post Like List
    // -----------------------

    @Test
    void getPostLikes_shouldReturnList() {

        likeService.likePost(post.getId(), user.getId());

        Slice<PostLikeResponse> slice =
                likeService.getPostLikesByPostId(
                        post.getId(),
                        PageRequest.of(0, 10)
                );

        assertThat(slice).isNotNull();
        assertThat(slice.getContent()).hasSize(1);

        PostLikeResponse res =
                slice.getContent().get(0);

        assertThat(res.userId()).isEqualTo(user.getId());
    }


    // -----------------------
    // Comment Like
    // -----------------------

    @Test
    void likeComment_shouldCreateLike() {

        LikeResponse res =
                likeService.likeComment(comment.getId(), user.getId());

        assertThat(res.liked()).isTrue();
        assertThat(res.likeCount()).isEqualTo(1);

        assertThat(
                commentLikeRepository.findByCommentIdAndUserId(
                        comment.getId(), user.getId()
                )
        ).isPresent();
    }


    @Test
    void unlikeComment_shouldRemoveLike() {

        likeService.likeComment(comment.getId(), user.getId());

        LikeResponse res =
                likeService.unlikeComment(comment.getId(), user.getId());

        assertThat(res.liked()).isFalse();
        assertThat(res.likeCount()).isEqualTo(0);

        assertThat(
                commentLikeRepository.findByCommentIdAndUserId(
                        comment.getId(), user.getId()
                )
        ).isEmpty();
    }


    // -----------------------
    // Comment Like List
    // -----------------------

    @Test
    void getCommentLikes_shouldReturnList() {

        likeService.likeComment(comment.getId(), user.getId());

        Slice<CommentLikeResponse> slice =
                likeService.getCommentLikesByCommentId(
                        comment.getId(),
                        PageRequest.of(0, 10)
                );

        assertThat(slice).isNotNull();
        assertThat(slice.getContent()).hasSize(1);

        CommentLikeResponse res =
                slice.getContent().getFirst();

        assertThat(res.userId()).isEqualTo(user.getId());
    }

}
