package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.UserRole;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    default String map(UserRole role) {
        return role == null ? null : role.getValue();
    }

    default UserRole map(String value) {
        return value == null ? null : UserRole.of(value);
    }
}
