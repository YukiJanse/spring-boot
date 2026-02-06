package se.jensen.yuki.springboot.user.mapper;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.user.infrastructure.persistence.User;
import se.jensen.yuki.springboot.user.web.dto.*;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    //    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "enabled", ignore = true)
//    @Mapping(target = "posts", ignore = true)
    User toUser(UserRequestDTO dto);

    UserProfileResponse toResponse(User user);

    void FromUpdateProfileRequest(UserUpdateProfileRequest dto, @MappingTarget User user);

    void FromUpdateEmailRequest(@Valid UserUpdateEmailRequest request, @MappingTarget User user);

    @Mapping(target = "password", source = "request.newPassword")
    void FromUpdatePasswordRequest(@Valid UserUpdatePasswordRequest request, @MappingTarget User user);
}
