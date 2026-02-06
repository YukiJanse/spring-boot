package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.dto.like.CommentLikeResponse;
import se.jensen.yuki.springboot.dto.like.LikeResponse;
import se.jensen.yuki.springboot.dto.like.PostLikeResponse;
import se.jensen.yuki.springboot.model.CommentLike;
import se.jensen.yuki.springboot.model.PostLike;
import se.jensen.yuki.springboot.repository.CommentLikeRepository;
import se.jensen.yuki.springboot.repository.CommentRepository;
import se.jensen.yuki.springboot.repository.PostLikeRepository;
import se.jensen.yuki.springboot.repository.PostRepository;
import se.jensen.yuki.springboot.user.usecase.UserQueryService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {
    private final PostLikeRepository postLikeRepository;
    private final UserQueryService userQueryService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    @Transactional
    public LikeResponse likePost(Long postId, Long userId) {
        if (postLikeRepository.findByPostIdAndUserId(postId, userId).isPresent()) {
            return new LikeResponse(true, postLikeRepository.countByPostId(postId));
        } else {
            PostLike postLike = new PostLike();
            postLike.setPost(postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found")));
            postLike.setUser(userQueryService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
            postLikeRepository.save(postLike);
            return new LikeResponse(true, postLikeRepository.countByPostId(postId));
        }
    }

    @Transactional
    public @Nullable LikeResponse unlikePost(Long postId, Long userId) {
        Optional<PostLike> existing = postLikeRepository.findByPostIdAndUserId(postId, userId);
        if (existing.isPresent()) {
            postLikeRepository.delete(existing.get());
            return new LikeResponse(false, postLikeRepository.countByPostId(postId));
        } else {
            return new LikeResponse(false, postLikeRepository.countByPostId(postId));
        }
    }

    @Nullable
    public Slice<PostLikeResponse> getPostLikesByPostId(Long postId,
                                                        @PageableDefault(size = 20,
                                                                sort = "createdAt",
                                                                direction = Sort.Direction.DESC)
                                                        Pageable pageable) {
        return postLikeRepository.findPostLikesByPostId(postId, pageable);
    }

    @Transactional
    public @Nullable LikeResponse likeComment(Long commentId, Long userId) {
        if (commentLikeRepository.findByCommentIdAndUserId(commentId, userId).isPresent()) {
            return new LikeResponse(true, commentLikeRepository.countByCommentId(commentId));
        } else {
            CommentLike cl = new CommentLike();
            cl.setComment(commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment not found")));
            cl.setUser(userQueryService.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found")));
            commentLikeRepository.save(cl);
            return new LikeResponse(true, commentLikeRepository.countByCommentId(commentId));
        }
    }

    @Transactional
    public @Nullable LikeResponse unlikeComment(Long commentId, Long userId) {
        Optional<CommentLike> existing = commentLikeRepository.findByCommentIdAndUserId(commentId, userId);
        if (existing.isPresent()) {
            commentLikeRepository.delete(existing.get());
            return new LikeResponse(false, commentLikeRepository.countByCommentId(commentId));
        } else {
            return new LikeResponse(false, commentLikeRepository.countByCommentId(commentId));
        }
    }

    @Nullable
    public Slice<CommentLikeResponse> getCommentLikesByCommentId(Long commentId,
                                                                 @PageableDefault(size = 20,
                                                                         sort = "createdAt",
                                                                         direction = Sort.Direction.DESC)
                                                                 Pageable pageable) {
        return commentLikeRepository.findCommentLikesByCommentId(commentId, pageable);
    }


}
