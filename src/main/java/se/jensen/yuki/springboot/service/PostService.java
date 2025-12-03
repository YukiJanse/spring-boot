package se.jensen.yuki.springboot.service;

import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.DTO.PostRequestDTO;
import se.jensen.yuki.springboot.DTO.PostResponseDTO;
import se.jensen.yuki.springboot.model.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();

    public List<PostResponseDTO> getAllPosts() {
        return posts.stream()
                .map(this::fromPost)
                .toList();
    }

    public PostResponseDTO getPostById(Long id) {
        if (id == null || id < 0 || id >= posts.size()) {
            throw new NoSuchElementException("No posts found");
        }
        return fromPost(posts.get(id.intValue()));
    }

    public PostResponseDTO addPost(PostRequestDTO requestDTO) {
        Post post = fromRequest(requestDTO);
        posts.add(post);
        return fromPost(post);
    }

    public PostResponseDTO updatePost(Long id, PostRequestDTO requestDTO) {
        if (id == null || id < 0 || id >= posts.size()) {
            throw new NoSuchElementException("No posts found");
        }
        Post post = fromRequest(requestDTO);
        posts.set(id.intValue(), post);
        return fromPost(posts.get(id.intValue()));
    }

    public void deletePost(Long id) {
        if (id == null || id < 0 || id >= posts.size()) {
            throw new NoSuchElementException("No posts found");
        }
        posts.remove(id.intValue());
    }

    private Post fromRequest(PostRequestDTO requestDTO) {
        return new Post(0L, requestDTO.text(), LocalDateTime.now());
    }

    private PostResponseDTO fromPost(Post post) {
        return new PostResponseDTO(post.getId(), post.getText(), post.getCreatedAt());
    }
}
