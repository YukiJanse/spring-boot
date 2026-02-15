package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.UserId;

@Mapper(componentModel = "spring")
public interface UserIdMapper {
    default Long map(UserId userId) {
        return userId == null ? null : userId.getValue();
    }

    default UserId map(Long value) {
        return value == null ? null : UserId.of(value);
    }
}
