package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.like.LikeResponse;
import se.jensen.yuki.springboot.dto.like.PostLikeResponse;
import se.jensen.yuki.springboot.model.PostLike;
import se.jensen.yuki.springboot.repository.CommentLikeRepository;
import se.jensen.yuki.springboot.repository.PostLikeRepository;
import se.jensen.yuki.springboot.repository.PostRepository;
import se.jensen.yuki.springboot.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public LikeResponse likePost(Long postId, Long userId) {
        Optional<PostLike> existing = postLikeRepository.findByPostIdAndUserId(postId, userId);
        boolean liked;

        if (existing.isPresent()) {
            postLikeRepository.delete(existing.get());
            liked = false;
        } else {
            PostLike postLike = new PostLike();
            postLike.setPost(postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found")));
            postLike.setUser(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
            postLikeRepository.save(postLike);
            liked = true;
        }

        return new LikeResponse(liked, postLikeRepository.countByPostId(postId));
    }

    @Nullable
    public Slice<PostLikeResponse> getPostLikesByPostId(Long postId,
                                                        @PageableDefault(size = 20,
                                                                sort = "createdAt",
                                                                direction = Sort.Direction.DESC)
                                                        Pageable pageable) {
        return postLikeRepository.findPostLikesByPostId(postId, pageable);
    }
}
