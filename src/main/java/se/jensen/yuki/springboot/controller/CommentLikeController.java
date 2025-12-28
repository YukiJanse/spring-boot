package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.like.CommentLikeResponse;
import se.jensen.yuki.springboot.dto.like.LikeResponse;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.LikeService;

@RestController
@RequestMapping("/comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {
    private final LikeService likeService;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<Slice<CommentLikeResponse>> getCommentLikes(@PathVariable Long commentId,
                                                                      Pageable pageable) {
        return ResponseEntity.ok().body(likeService.getCommentLikesByCommentId(commentId, pageable));
    }

    @PostMapping
    public ResponseEntity<LikeResponse> likeComment(@PathVariable Long commentId) {
        Long userId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(likeService.likeComment(commentId, userId));
    }

    @DeleteMapping
    public ResponseEntity<LikeResponse> unLikeComment(@PathVariable Long commentId) {
        Long userId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(likeService.unlikeComment(commentId, userId));
    }
}
