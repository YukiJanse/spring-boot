package se.jensen.yuki.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.yuki.springboot.model.CommentLike;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
}
