package se.jensen.yuki.springboot.user.domain.vo.mapper;

import org.mapstruct.Mapper;
import se.jensen.yuki.springboot.user.domain.vo.HashedPassword;

@Mapper(componentModel = "spring")
public interface PasswordMapper {
    default String map(HashedPassword password) {
        return password == null ? null : password.getValue();
    }

    default HashedPassword map(String value) {
        return value == null ? null : HashedPassword.of(value);
    }
}
