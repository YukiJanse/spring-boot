package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.dto.mute.MuteMutingResponse;
import se.jensen.yuki.springboot.dto.mute.MuteResponse;
import se.jensen.yuki.springboot.repository.MuteRepository;

@Service
@RequiredArgsConstructor
public class MuteService {
    private final MuteRepository muteRepository;

    public @Nullable Slice<MuteMutingResponse> getMutings(Long currentUserId, Pageable pageable) {
        return muteRepository.findMutingsByUserId(currentUserId, pageable);
    }

    public @Nullable MuteResponse muteUser(Long currentUserId, Long targetUserId) {
        if (muteRepository.existsByMutingIdAndMutedId(currentUserId, targetUserId)) {
            return muteRepository.findByMutingIdAndMutedId(currentUserId, targetUserId);
        } else {
            // Create mute relationship
            muteRepository.createMuteRelationship(currentUserId, targetUserId);
            return muteRepository.findByMutingIdAndMutedId(currentUserId, targetUserId);
        }
    }

    public boolean unmuteUser(Long currentUserId, Long targetUserId) {
        if (muteRepository.existsByMutingIdAndMutedId(currentUserId, targetUserId)) {
            muteRepository.deleteMuteRelationship(currentUserId, targetUserId);
            return true;
        } else {
            return false;
        }
    }
}
