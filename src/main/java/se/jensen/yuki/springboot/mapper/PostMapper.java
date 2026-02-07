package se.jensen.yuki.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.dto.post.PostCreateRequest;
import se.jensen.yuki.springboot.dto.post.PostCreateResponse;
import se.jensen.yuki.springboot.dto.post.PostFeedResponse;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", expression = "java(author)")
    @Mapping(target = "originalPost", ignore = true)
    Post PostCreateToPost(PostCreateRequest requestDTO, UserJpaEntity author);

    PostCreateResponse toCreateResponse(Post post);


    PostFeedResponse toFeedResponse(Post post);
}
