package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.dto.block.BlockBlockingResponse;
import se.jensen.yuki.springboot.dto.block.BlockResponse;
import se.jensen.yuki.springboot.repository.BlockRepository;

@Service
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;

    public @Nullable Slice<BlockBlockingResponse> getBlockings(Long currentUserId, Pageable pageable) {
        return blockRepository.findBlockingsByUserId(currentUserId, pageable);
    }

    public @Nullable BlockResponse blockUser(Long currentUserId, Long targetUserId) {
        if (blockRepository.existsByBlockingIdAndBlockedId(currentUserId, targetUserId)) {
            return blockRepository.findByBlockingIdAndBlockedId(currentUserId, targetUserId);
        } else {
            blockRepository.createBlockRelationship(currentUserId, targetUserId);
            return blockRepository.findByBlockingIdAndBlockedId(currentUserId, targetUserId);
        }
    }

    public boolean unblockUser(Long currentUserId, Long targetUserId) {
        if (blockRepository.existsByBlockingIdAndBlockedId(currentUserId, targetUserId)) {
            blockRepository.deleteBlockRelationship(currentUserId, targetUserId);
            return true;
        } else {
            return false;
        }
    }
}
