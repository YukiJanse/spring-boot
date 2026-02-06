package se.jensen.yuki.springboot.mapper;

import org.jspecify.annotations.Nullable;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.dto.comment.CommentCreateRequest;
import se.jensen.yuki.springboot.dto.comment.CommentCreateResponse;
import se.jensen.yuki.springboot.dto.comment.CommentReplyResponse;
import se.jensen.yuki.springboot.dto.comment.CommentUpdateResponse;
import se.jensen.yuki.springboot.model.Comment;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CommentMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "deleted", expression = "java(false)")
    @Mapping(target = "edited", expression = "java(false)")
    Comment createRequestToComment(Post post, UserJpaEntity author, CommentCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "content", source = "request.content")
    @Mapping(target = "parent", source = "parent")
    @Mapping(target = "deleted", expression = "java(false)")
    @Mapping(target = "edited", expression = "java(false)")
    Comment createRequestToComment(Post post, UserJpaEntity author, Comment parent, CommentCreateRequest request);

    @Mapping(target = "authorUserId", source = "comment.author.id")
    @Mapping(target = "authorUsername", source = "comment.author.username")
    @Mapping(target = "postId", source = "comment.post.id")
    @Mapping(target = "likeCount", expression = "java(0L)")
    @Mapping(target = "commentCount", expression = "java(0L)")
    CommentCreateResponse toCreateResponse(Comment comment);


    CommentReplyResponse toCommentReplyResponse(Comment comment);

    @Nullable
    CommentUpdateResponse toCommentUpdateResponse(Comment currentComment);
}
