package se.jensen.yuki.springboot.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.DTO.PostRequestDTO;
import se.jensen.yuki.springboot.DTO.PostResponseDTO;
import se.jensen.yuki.springboot.service.PostService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        return ResponseEntity.ok().body(service.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> addPost(@Valid @RequestBody PostRequestDTO postDTO) {
        return ResponseEntity
                .ok()
                .body(service.addPost(postDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .ok()
                    .body(service.getPostById(id));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updatePost(@PathVariable Long id, @Valid @RequestBody PostRequestDTO updatePost) {
        try {
            return ResponseEntity
                    .ok()
                    .body(service.updatePost(id, updatePost));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDTO> deletePost(@PathVariable Long id) {
        try {
            service.deletePost(id);
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }
}
