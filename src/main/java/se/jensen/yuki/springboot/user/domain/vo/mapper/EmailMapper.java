package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.Email;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    default String map(Email email) {
        return email == null ? null : email.getValue();
    }

    default Email map(String value) {
        return value == null ? null : Email.of(value);
    }
}
