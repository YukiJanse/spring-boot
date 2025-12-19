package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.comment.*;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.CommentService;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<CommentCreateResponse> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest request) {
        Long userId = authService.getCurrentUserId();
        CommentCreateResponse response = commentService.addComment(postId, userId, request);
        return ResponseEntity
                .created(URI.create("/posts/" + postId + "/comments/" + response.id()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<Slice<CommentDetailResponse>> getPostComments(@PathVariable Long postId, Pageable pageable) {
        return ResponseEntity
                .ok()
                .body(commentService.getPostComments(postId, pageable));
    }

    @GetMapping("{commentId}")
    public ResponseEntity<List<CommentReplyResponse>> getReplyComments(@PathVariable Long commentId) {
        return ResponseEntity
                .ok()
                .body(commentService.getReplyComments(commentId));
    }

    @PutMapping("{commentId}")
    public ResponseEntity<CommentDetailResponse> updateComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest request) {
        Long userId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(commentService.updateComment(commentId, request, userId));
    }

    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        Long userId = authService.getCurrentUserId();
        commentService.deleteComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }
}
