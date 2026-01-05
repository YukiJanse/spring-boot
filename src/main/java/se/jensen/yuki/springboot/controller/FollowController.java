package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.follow.FollowFollowedResponse;
import se.jensen.yuki.springboot.dto.follow.FollowFollowerResponse;
import se.jensen.yuki.springboot.dto.follow.FollowResponse;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.FollowService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {
    private final FollowService followService;
    private final AuthService authService;

    // GET /follows/followers
    @GetMapping("/followers")
    public ResponseEntity<Slice<FollowFollowerResponse>> getFollowers(Pageable pageable) {
        Long currentUserId = authService.getCurrentUserId();
        Long targetUserId = currentUserId;
        return ResponseEntity.ok().body(followService.getFollowers(targetUserId, currentUserId, pageable));
    }

    // GET /follows/{userId}/followers
    @GetMapping("/{userId}/followers")
    public ResponseEntity<Slice<FollowFollowerResponse>> getFollowers(@PathVariable Long targetUserId, Pageable pageable) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(followService.getFollowers(targetUserId, currentUserId, pageable));
    }

    // GET /follows/following
    @GetMapping("/following")
    public ResponseEntity<Slice<FollowFollowedResponse>> getFollowing(Pageable pageable) {
        Long currentUserId = authService.getCurrentUserId();
        Long targetUserId = currentUserId;
        return ResponseEntity.ok().body(followService.getFollowing(targetUserId, currentUserId, pageable));
    }

    // GET /follows/{userId}/following
    @GetMapping("/{userId}/following")
    public ResponseEntity<Slice<FollowFollowedResponse>> getFollowing(@PathVariable Long targetUserId, Pageable pageable) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(followService.getFollowing(targetUserId, currentUserId, pageable));
    }

    // POST /follows/{userId}
    @PostMapping("/{userId}")
    public ResponseEntity<FollowResponse> followUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        System.out.println("Current User ID: " + currentUserId + ", Target User ID: " + userId);
        return ResponseEntity.ok().body(followService.followUser(currentUserId, userId));
    }

    // DELETE /follows/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<FollowResponse> unfollowUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(followService.unfollowUser(currentUserId, userId));
    }
}
