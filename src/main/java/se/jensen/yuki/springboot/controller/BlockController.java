package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.block.BlockBlockingResponse;
import se.jensen.yuki.springboot.dto.block.BlockResponse;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.BlockService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blocks")
public class BlockController {
    private final BlockService blockService;
    private final AuthService authService;

    @GetMapping("/blockings")
    public ResponseEntity<Slice<BlockBlockingResponse>> getBlockings(Pageable pageable) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(blockService.getBlockings(currentUserId, pageable));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<BlockResponse> blockUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(blockService.blockUser(currentUserId, userId));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> unblockUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        return blockService.unblockUser(currentUserId, userId) ?
                ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
