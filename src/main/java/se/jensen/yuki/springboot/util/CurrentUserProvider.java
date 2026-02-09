package se.jensen.yuki.springboot.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.user.usecase.UserQueryService;


@Component
@RequiredArgsConstructor
public class CurrentUserProvider {
    private final UserQueryService UserQueryService;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return UserQueryService.findByEmail(email).getId();
    }
}
