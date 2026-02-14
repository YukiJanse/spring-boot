package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.Bio;

@Mapper(componentModel = "spring")
public interface BioMapper {
    default String map(Bio bio) {
        return bio == null ? null : bio.getValue();
    }

    default Bio map(String value) {
        return value == null ? null : Bio.of(value);
    }
}
