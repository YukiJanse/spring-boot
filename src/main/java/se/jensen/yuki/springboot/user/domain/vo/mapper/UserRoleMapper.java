package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.UserRole;

/**
 * MapStruct mapper for converting between UserRole value object and String.
 */
@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    /**
     * Maps a UserRole value object to its String representation.
     *
     * @param role the UserRole to map
     * @return the String representation of the UserRole, or null if the input is null
     */
    default String map(UserRole role) {
        return role == null ? null : role.getValue();
    }

    /**
     * Maps a String to a UserRole value object.
     *
     * @param value the String to map
     * @return the UserRole value object, or null if the input is null
     */
    default UserRole map(String value) {
        return value == null ? null : UserRole.of(value);
    }
}
