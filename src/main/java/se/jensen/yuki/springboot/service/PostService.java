package se.jensen.yuki.springboot.service;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.post.PostCreateRequest;
import se.jensen.yuki.springboot.dto.post.PostCreateResponse;
import se.jensen.yuki.springboot.dto.post.PostFeedResponse;
import se.jensen.yuki.springboot.exception.PostNotFoundException;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.mapper.PostMapper;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.repository.PostRepository;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserRepository;

import java.util.List;

@Service
public class PostService {
    private final Logger log = LoggerFactory.getLogger(PostService.class);
    private final PostMapper postMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostMapper postMapper, PostRepository postRepository, UserRepository userRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    /**
     * MUST MODIFY RETURN TYPE
     *
     * @return
     */
    public List<PostCreateResponse> getAllPosts() {
        log.info("Starting to get all posts");
        return postRepository.findAll()
                .stream()
                .map(postMapper::toCreateResponse)
                .toList();
    }

    public PostFeedResponse getPostById(Long id) {
        log.info("Starting to get a post by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to get a post with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }
        return postRepository.findPostById(id)
                .orElseThrow(() -> new PostNotFoundException("No post found with id=" + id));
    }

    public PostFeedResponse addPost(Long userId, PostCreateRequest requestDTO) {
        log.info("Starting to add a post");
        UserJpaEntity author = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("The post's author doesn't exist"));
        Post post = postMapper.PostCreateToPost(requestDTO, author);
        postRepository.save(post);
        log.info("Added a post successfully");
        log.debug("savedPost: content={}, author={}", post.getContent(), post.getUser().getUsername());
        return postRepository.findPostById(post.getId()).orElseThrow(() -> new PostNotFoundException("Couldn't return the created post"));
    }

    public PostFeedResponse updatePost(Long postId, PostCreateRequest requestDTO, Long userId) {
        log.info("Starting to update a post with ID={}", postId);
        // ID validation
        if (postId == null || postId < 0) {
            log.warn("Tried to update a post with invalid ID={}", postId);
            throw new IllegalArgumentException("Invalid Post ID");
        } else if (userId == null || userId < 0) {
            log.warn("Tried to update a post with invalid ID={}", userId);
            throw new IllegalArgumentException("Invalid User ID");
        }

        UserJpaEntity author = userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException("The request author doesn't exist"));
        Post currentPost = postRepository
                .findById(postId)
                .orElseThrow(() -> new PostNotFoundException("No post found with id= " + postId));

        // Check if the post's author and request author are the same
        if (!currentPost.getUser().getId().equals(author.getId())) {
            throw new IllegalArgumentException("Wrong Post ID or User ID");
        }

        currentPost.setContent(requestDTO.content());
        postRepository.save(currentPost);
        log.info("Updated a post successfully with ID={}", postId);
        return postRepository.findPostById(currentPost.getId())
                .orElseThrow(() -> new PostNotFoundException("Couldn't return the created post"));
    }

    public void deletePost(Long id, Long authorId) {
        log.info("Starting to delete a post by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to delete a post with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }


        Post targetPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("The post with ID=" + id + " doesn't exist"));
        // If it is the correct author
        if (!targetPost.getUser().getId().equals(authorId)) {
            throw new IllegalArgumentException("The user with id= " + authorId + " isn't allowed to delete the post with id=" + id);
        }

        try {
            postRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("No post found with id= " + id);
        }
        log.info("Deleted a post successfully by ID={}", id);
    }

    @Transactional(readOnly = true)
    public Slice<PostFeedResponse> getFeed(Long userId, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postRepository.findFeedPosts(userId, pageable);
    }

    @Transactional(readOnly = true)
    public @Nullable Slice<PostFeedResponse> getUserPosts(Long userId, @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postRepository.findUserPosts(userId, pageable);
    }
}
