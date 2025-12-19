package se.jensen.yuki.springboot.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.post.PostCreateRequest;
import se.jensen.yuki.springboot.dto.post.PostCreateResponse;
import se.jensen.yuki.springboot.dto.post.PostFeedResponse;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.PostService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService postService;
    private final AuthService authService;

    @GetMapping("/admin/posts")
    public ResponseEntity<List<PostCreateResponse>> getAllPosts() {
        log.info("Starting to get all posts");
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostFeedResponse> createPost(@Valid @RequestBody PostCreateRequest postDTO) {
        Long userId = authService.getCurrentUserId();

        log.info("Starting to add a post");
        PostFeedResponse responseDTO = postService.addPost(userId, postDTO);
        return ResponseEntity
                .created(URI.create("/posts/" + responseDTO.id()))
                .body(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostFeedResponse> getPostById(@PathVariable Long id) {
        log.info("Starting to get a post by ID={}", id);
        return ResponseEntity
                .ok()
                .body(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostFeedResponse> updatePost(@PathVariable Long id, @Valid @RequestBody PostCreateRequest updatePost) {
        Long userId = authService.getCurrentUserId();
        log.info("Stating to update post by ID={}", id);
        return ResponseEntity
                .ok()
                .body(postService.updatePost(id, updatePost, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Long userId = authService.getCurrentUserId();
        log.info("Starting to delete a post by id={}", id);
        postService.deletePost(id, userId);
        log.info("Deleted a post successfully with id={}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/feed")
    public ResponseEntity<Slice<PostFeedResponse>> getFeed(Pageable pageable) {
        Long userId = authService.getCurrentUserId();
        return ResponseEntity
                .ok()
                .body(postService.getFeed(userId, pageable));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Slice<PostFeedResponse>> getUserPosts(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(postService.getUserPosts(userId, pageable));
    }
}
