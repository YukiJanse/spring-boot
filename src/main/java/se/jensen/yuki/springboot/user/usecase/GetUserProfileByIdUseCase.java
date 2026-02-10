package se.jensen.yuki.springboot.user.usecase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.jensen.yuki.springboot.user.web.dto.UserProfileResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserProfileByIdUseCase {
    private final UserQueryService userQueryService;

    @Transactional(readOnly = true)
    public UserProfileResponse execute(Long id) {
        log.debug("Starting to get a user by ID={}", id);

        return userQueryService.findById(id);
    }
}
