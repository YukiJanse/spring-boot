package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.Bio;

/**
 * MapStruct mapper for converting between Bio value object and String.
 */
@Mapper(componentModel = "spring")
public interface BioMapper {
    /**
     * Maps a Bio value object to its String representation.
     *
     * @param bio the Bio to map
     * @return the String representation of the Bio, or null if the input is null
     */
    default String map(Bio bio) {
        return bio == null ? null : bio.getValue();
    }

    /**
     * Maps a String to a Bio value object.
     *
     * @param value the String to map
     * @return the Bio value object, or null if the input is null
     */
    default Bio map(String value) {
        return value == null ? null : Bio.of(value);
    }
}
