package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.DisplayName;

/**
 * MapStruct mapper for converting between DisplayName value object and String.
 */
@Mapper(componentModel = "spring")
public interface DisplayNameMapper {
    /**
     * Maps a DisplayName value object to its String representation.
     *
     * @param displayName the DisplayName to map
     * @return the String representation of the DisplayName, or null if the input is null
     */
    default String map(DisplayName displayName) {
        return displayName == null ? null : displayName.getValue();
    }

    /**
     * Maps a String to a DisplayName value object.
     *
     * @param displayName the String to map
     * @return the DisplayName value object, or null if the input is null
     */
    default DisplayName map(String displayName) {
        return displayName == null ? null : DisplayName.of(displayName);
    }
}
