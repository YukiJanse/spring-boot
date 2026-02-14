package se.jensen.yuki.springboot.user.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.user.domain.User;
import se.jensen.yuki.springboot.user.domain.vo.HashedPassword;
import se.jensen.yuki.springboot.user.domain.vo.mapper.EmailMapper;
import se.jensen.yuki.springboot.user.domain.vo.mapper.UsernameMapper;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;
import se.jensen.yuki.springboot.user.web.dto.UserRequestDTO;

@Mapper(componentModel = "spring",
        uses = {EmailMapper.class, UsernameMapper.class, HashedPassword.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserResponseMapper {

    User toUser(UserRequestDTO dto);

    UserProfileResponse toResponse(User user);
}
