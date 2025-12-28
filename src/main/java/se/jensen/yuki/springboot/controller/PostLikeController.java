package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.like.LikeResponse;
import se.jensen.yuki.springboot.dto.like.PostLikeResponse;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.LikeService;

@RestController
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {
    private final LikeService likeService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Slice<PostLikeResponse>> getPostLikes(@PathVariable Long postId,
                                                                Pageable pageable) {
        return ResponseEntity.ok().body(likeService.getPostLikesByPostId(postId, pageable));
    }

    @PostMapping
    public ResponseEntity<LikeResponse> likePost(@PathVariable Long postId) {
        Long userId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(likeService.likePost(postId, userId));
    }

    @DeleteMapping
    public ResponseEntity<LikeResponse> unLikePost(@PathVariable Long postId) {
        Long userId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(likeService.unlikePost(postId, userId));
    }
}
