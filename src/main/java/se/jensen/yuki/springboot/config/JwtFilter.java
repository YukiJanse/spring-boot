package se.jensen.yuki.springboot.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.model.SecurityUser;
import se.jensen.yuki.springboot.service.JwtService;
import se.jensen.yuki.springboot.user.infrastructure.jpa.UserJpaEntity;
import se.jensen.yuki.springboot.user.usecase.UserLoadService;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserLoadService userLoadService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        log.debug("Starting JWT filter for request: {}", request.getRequestURI());
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        if (!jwtService.validateToken(jwt)) {
            // invalid token: do not set authentication, just continue
            log.warn("Invalid JWT token");
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.extractUserId(jwt);


        try {
            UserJpaEntity user = userLoadService.requireJpaById(userId);

            SecurityUser securityUser = new SecurityUser(user.getId(), user.getEmail(), user.getRole(), user.getPassword());
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            securityUser,
                            null,
                            securityUser.getAuthorities()
                    );

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (UserNotFoundException e) {
            log.warn("User not found for ID extracted from JWT: {}", userId);
            SecurityContextHolder.clearContext();
            // no response manipulation → treat as unauthenticated
        }
        filterChain.doFilter(request, response);
    }
}
