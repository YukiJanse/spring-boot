package se.jensen.yuki.springboot.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.DTO.PostRequestDTO;
import se.jensen.yuki.springboot.DTO.PostResponseDTO;
import se.jensen.yuki.springboot.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        log.info("Starting to get all posts");
        return ResponseEntity.ok().body(service.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(@Valid @RequestBody PostRequestDTO postDTO) {
        log.info("Starting to add a post");
        return ResponseEntity
                .ok()
                .body(service.addPost(postDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        log.info("Starting to get a post by ID={}", id);
        return ResponseEntity
                .ok()
                .body(service.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequestDTO updatePost) {
        log.info("Stating to update post by ID={}", id);
        return ResponseEntity
                .ok()
                .body(service.updatePost(id, updatePost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable Long id) {
        log.info("Starting to delete a post by id={}", id);
        service.deletePost(id);
        log.info("Deleted a post successfully with id={}", id);
        return ResponseEntity.noContent().build();
    }
}
