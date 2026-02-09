package se.jensen.yuki.springboot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.model.SecurityUser;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.usecase.UserQueryService;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserQueryService userQueryService;

    @Override
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {
        UserJpaEntity user = userQueryService.findByEmail(email);
        return new SecurityUser(user);
    }
}
