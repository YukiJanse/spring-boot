package se.jensen.yuki.springboot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.DTO.PostRequestDTO;
import se.jensen.yuki.springboot.DTO.PostResponseDTO;
import se.jensen.yuki.springboot.exception.PostNotFoundException;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.model.User;
import se.jensen.yuki.springboot.repository.PostRepository;
import se.jensen.yuki.springboot.repository.UserRepository;
import se.jensen.yuki.springboot.util.PostMapper;

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

    public List<PostResponseDTO> getAllPosts() {
        log.info("Starting to get all posts");
        return postRepository.findAll()
                .stream()
                .map(postMapper::toResponse)
                .toList();
    }

    public PostResponseDTO getPostById(Long id) {
        log.info("Starting to get a post by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to get a post with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }
        return postMapper.toResponse(
                postRepository.findById(id)
                        .orElseThrow(() -> new PostNotFoundException("No post found with id=" + id)));
    }

    public PostResponseDTO addPost(PostRequestDTO requestDTO) {
        log.info("Starting to add a post");
        User author = userRepository.findById(requestDTO.userId()).orElseThrow(() -> new UserNotFoundException("The post's author doesn't exist"));
        Post post = postMapper.toPost(requestDTO, author);
        Post savedPost = postRepository.save(post);
        log.info("Added a post successfully");
        return postMapper.toResponse(savedPost);
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO requestDTO) {
        log.info("Starting to update a post with ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to update a post with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }
        User author = userRepository.findById(requestDTO.userId()).orElseThrow(() -> new UserNotFoundException("The post's author doesn't exist"));
        Post post = postMapper.toPost(requestDTO, author);
        Post currentPost = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("No post found with id= " + id));
        currentPost.setContents(post.getContents());
        log.info("Updated a post successfully with ID={}", id);
        return postMapper.toResponse(postRepository.save(currentPost));
    }

    public void deletePost(Long id) {
        log.info("Starting to delete a post by ID={}", id);
        if (id == null || id < 0) {
            log.warn("Tried to delete a post with invalid ID={}", id);
            throw new IllegalArgumentException("Invalid ID");
        }

        try {
            postRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new PostNotFoundException("No post found with id= " + id);
        }
        log.info("Deleted a post successfully by ID={}", id);
    }


}
