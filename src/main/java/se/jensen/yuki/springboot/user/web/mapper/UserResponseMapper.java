package se.jensen.yuki.springboot.user.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.vo.mapper.EmailMapper;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserRequestDTO;

@Mapper(componentModel = "spring",
        uses = {EmailMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserResponseMapper {

    User toUser(UserRequestDTO dto);

    UserProfileResponse toResponse(User user);
}
