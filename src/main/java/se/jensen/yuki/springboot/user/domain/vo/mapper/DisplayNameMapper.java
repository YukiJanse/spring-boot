package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.DisplayName;

@Mapper(componentModel = "spring")
public interface DisplayNameMapper {
    default String map(DisplayName displayName) {
        return displayName == null ? null : displayName.getValue();
    }

    default DisplayName map(String displayName) {
        return displayName == null ? null : DisplayName.of(displayName);
    }
}
