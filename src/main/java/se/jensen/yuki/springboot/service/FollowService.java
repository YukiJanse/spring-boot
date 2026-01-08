package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.dto.follow.FollowFollowedResponse;
import se.jensen.yuki.springboot.dto.follow.FollowFollowerResponse;
import se.jensen.yuki.springboot.dto.follow.FollowResponse;
import se.jensen.yuki.springboot.repository.FollowRepository;


@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public @Nullable Slice<FollowFollowerResponse> getFollowers(Long currentUserId, Long targetUserId, Pageable pageable) {
        return followRepository.findFollowersByUserId(currentUserId, targetUserId, pageable);
    }

    public @Nullable Slice<FollowFollowedResponse> getFollowing(Long currentUserId, Long targetUserId, Pageable pageable) {
        return followRepository.findFollowedByUserId(currentUserId, targetUserId, pageable);
    }

    public @Nullable FollowResponse followUser(Long currentUserId, Long userId) {
        if (followRepository.existsByFollowerIdAndFollowedId(currentUserId, userId)) {
            return followRepository.findByFollowerIdAndFollowedId(currentUserId, userId);
        } else {
            // Create follow relationship
            followRepository.createFollowRelationship(currentUserId, userId);
            return followRepository.findByFollowerIdAndFollowedId(currentUserId, userId);
        }
    }

    public boolean unfollowUser(Long currentUserId, Long userId) {
        if (followRepository.existsByFollowerIdAndFollowedId(currentUserId, userId)) {
            followRepository.deleteByFollowerIdAndFollowedId(currentUserId, userId);
            return true;
        } else {
            return false;
        }
    }
}
