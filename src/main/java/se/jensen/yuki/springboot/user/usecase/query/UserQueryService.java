package se.jensen.yuki.springboot.user.usecase.query;

import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

import java.util.List;


public interface UserQueryService {

    List<UserProfileResponse> findAll();

    UserProfileResponse findById(Long id);
}
