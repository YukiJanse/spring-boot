package se.jensen.yuki.springboot.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import se.jensen.yuki.springboot.user.usecase.UserLoadService;


@Component
@RequiredArgsConstructor
public class CurrentUserProvider {
    private final UserLoadService userLoadService;

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userLoadService.requireJpaByEmail(email).getId();
    }
}
