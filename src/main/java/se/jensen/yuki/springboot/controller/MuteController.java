package se.jensen.yuki.springboot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.mute.MuteMutingResponse;
import se.jensen.yuki.springboot.dto.mute.MuteResponse;
import se.jensen.yuki.springboot.service.AuthService;
import se.jensen.yuki.springboot.service.MuteService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mutes")
public class MuteController {
    private final MuteService muteService;
    private final AuthService authService;

    // GET /mutes/mutes
    @GetMapping("/muteings")
    public ResponseEntity<Slice<MuteMutingResponse>> getMutings(Pageable pageable) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(muteService.getMutings(currentUserId, pageable));
    }

    // POST /mutes/{userId}
    @PostMapping("/{userId}")
    public ResponseEntity<MuteResponse> muteUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        return ResponseEntity.ok().body(muteService.muteUser(currentUserId, userId));
    }

    // DELETE /mutes/{userId}
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> unmuteUser(@PathVariable Long userId) {
        Long currentUserId = authService.getCurrentUserId();
        return muteService.unmuteUser(currentUserId, userId) ?
                ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
