package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.Username;

@Mapper(componentModel = "spring")
public interface UsernameMapper {
    default String map(Username username) {
        return username == null ? null : username.getValue();
    }

    default Username map(String username) {
        return username == null ? null : Username.of(username);
    }
}
