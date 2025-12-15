package se.jensen.yuki.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.DTO.post.PostRequestDTO;
import se.jensen.yuki.springboot.DTO.post.PostResponseDTO;
import se.jensen.yuki.springboot.model.Post;
import se.jensen.yuki.springboot.model.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PostMapper {

    Post toPost(PostRequestDTO requestDTO, User author);

    PostResponseDTO toResponse(Post post);
}
