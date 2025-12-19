package se.jensen.yuki.springboot.dto.comment;

public record CommentCreateRequest(String content, Long parentId) {
}
