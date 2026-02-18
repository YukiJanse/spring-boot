package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.AvatarUrl;

/**
 * MapStruct mapper for converting between AvatarUrl value object and String.
 */
@Mapper(componentModel = "spring")
public interface AvatarUrlMapper {
    /**
     * Maps an AvatarUrl value object to its String representation.
     *
     * @param avatarUrl the AvatarUrl to map
     * @return the String representation of the AvatarUrl, or null if the input is null
     */
    default String map(AvatarUrl avatarUrl) {
        return avatarUrl == null ? null : avatarUrl.getValue();
    }

    /**
     * Maps a String to an AvatarUrl value object.
     *
     * @param value the String to map
     * @return the AvatarUrl value object, or null if the input is null
     */
    default AvatarUrl map(String value) {
        return value == null ? null : AvatarUrl.of(value);
    }
}
