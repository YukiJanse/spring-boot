package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetAllUsersUseCase {
    private final UserQueryService userQueryService;

    @Transactional(readOnly = true)
    public List<UserProfileResponse> execute() {
        log.debug("Starting to get all users");

        return userQueryService.findAll();
    }
}
