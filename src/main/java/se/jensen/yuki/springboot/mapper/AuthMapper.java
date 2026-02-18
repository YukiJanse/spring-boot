package se.jensen.yuki.springboot.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import se.jensen.yuki.springboot.dto.auth.AuthRegisterRequestDTO;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AuthMapper {
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "displayName", source = "username")
    @Mapping(target = "bio", constant = "")
    UserJpaEntity registerDtoToUser(AuthRegisterRequestDTO dto);


//    User loginDtoToUser(LoginDTO dto);
//
//    LoginDTO toLoginDTO(UserJpaEntity user);

//    User toUser(AuthRegisterRequestDTO requestDTO) {
//        UserJpaEntity user = new User();
//        user.setUsername(requestDTO.username());
//        user.setEmail(requestDTO.email());
//        user.setPassword(requestDTO.password());
//        user.setDisplayName(requestDTO.username().substring(0, 3));
//        user.setRole("NONE");
//        user.setBio("Hello, I'm " + user.getDisplayName() + "!");
//        return user;
//    }

}
