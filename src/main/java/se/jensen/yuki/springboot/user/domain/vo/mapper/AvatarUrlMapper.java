package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.AvatarUrl;

@Mapper(componentModel = "spring")
public interface AvatarUrlMapper {
    default String map(AvatarUrl avatarUrl) {
        return avatarUrl == null ? null : avatarUrl.getValue();
    }

    default AvatarUrl map(String value) {
        return value == null ? null : AvatarUrl.of(value);
    }
}
