package se.jensen.yuki.springboot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import se.jensen.yuki.springboot.DTO.PostRequestDTO;
import se.jensen.yuki.springboot.DTO.PostResponseDTO;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.model.User;

import java.time.LocalDateTime;

@Component
public class PostMapper {
    private static final Logger log = LoggerFactory.getLogger(PostMapping.class);

    public Post toPost(PostRequestDTO requestDTO, User author) {
        log.info("Mapping Post from request (text={})", requestDTO.text());
        return new Post(null, requestDTO.text(), LocalDateTime.now(), 0, author);
    }

    public PostResponseDTO toResponse(Post post) {
        log.info("Mapping Response from Post (id={}, text={}, createdAt={})", post.getId(), post.getContents(), post.getCreatedAt());
        return new PostResponseDTO(post.getId(), post.getContents(), post.getCreatedAt(), post.getUser().getId());
    }
}
