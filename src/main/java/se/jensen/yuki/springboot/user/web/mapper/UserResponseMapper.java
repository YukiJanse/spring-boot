package se.jensen.yuki.springboot.user.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.vo.mapper.*;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

/**
 * MapStruct mapper for converting User domain model to UserProfileResponse DTO.
 */
@Mapper(componentModel = "spring",
        uses = {EmailMapper.class, UsernameMapper.class, HashedPasswordMapper.class, UserRoleMapper.class, DisplayNameMapper.class, BioMapper.class, AvatarUrlMapper.class, UserIdMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserResponseMapper {
    /**
     * Maps a User domain model to a UserProfileResponse DTO.
     *
     * @param user the User to map
     * @return the UserProfileResponse DTO, or null if the input is null
     */
    UserProfileResponse toResponse(User user);
}
