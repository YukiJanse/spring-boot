package se.jensen.yuki.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.jensen.yuki.springboot.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
}
