package se.jensen.yuki.springboot.user.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.vo.HashedPassword;
import se.jensen.yuki.springboot.user.domain.vo.mapper.*;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

@Mapper(componentModel = "spring",
        uses = {EmailMapper.class, UsernameMapper.class, HashedPassword.class, UserRoleMapper.class, DisplayNameMapper.class, BioMapper.class, AvatarUrlMapper.class, UserIdMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserResponseMapper {
    UserProfileResponse toResponse(User user);
}
