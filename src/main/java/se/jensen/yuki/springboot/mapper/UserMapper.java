package se.jensen.yuki.springboot.mapper;

import jakarta.validation.Valid;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.dto.user.*;
import se.jensen.yuki.springboot.model.User;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    //    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "enabled", ignore = true)
//    @Mapping(target = "posts", ignore = true)
    User toUser(UserRequestDTO dto);

    UserResponseDTO toResponse(User user);

    void FromUpdateProfileRequest(UserUpdateProfileRequest dto, @MappingTarget User user);

    void updateFromEmail(UpdateEmailDTO dto, @MappingTarget User user);

    void FromUpdateEmailRequest(@Valid UserUpdateEmailRequest request, @MappingTarget User user);

    @Mapping(target = "password", source = "request.newPassword")
    void FromUpdatePasswordRequest(@Valid UserUpdatePasswordRequest request, @MappingTarget User user);
}
