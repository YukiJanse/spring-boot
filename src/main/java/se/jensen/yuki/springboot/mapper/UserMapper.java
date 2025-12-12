package se.jensen.yuki.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.DTO.user.UpdateEmailDTO;
import se.jensen.yuki.springboot.DTO.user.UpdateUserDisplayNameDTO;
import se.jensen.yuki.springboot.DTO.user.UserRequestDTO;
import se.jensen.yuki.springboot.DTO.user.UserResponseDTO;
import se.jensen.yuki.springboot.model.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    //    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "enabled", ignore = true)
//    @Mapping(target = "posts", ignore = true)
    User toUser(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);

    void updateFromDisplayNameDTO(UpdateUserDisplayNameDTO dto, @MappingTarget User user);

    void updateFromEmail(UpdateEmailDTO dto, @MappingTarget User user);
}
