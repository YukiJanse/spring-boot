package se.jensen.yuki.springboot.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import se.jensen.yuki.springboot.exception.UnauthorizedException;
import se.jensen.yuki.springboot.model.SecurityUser;
import se.jensen.yuki.springboot.service.JwtService;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.usecase.UserQueryService;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserQueryService UserQueryService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        if (!jwtService.validateToken(jwt)) {
            // invalid token: do not set authentication, just continue
            filterChain.doFilter(request, response);
            return;
        }

        Long userId = jwtService.extractUserId(jwt);

        UserJpaEntity user = UserQueryService.findById(userId).orElseThrow(() -> new UnauthorizedException("User not found"));

        SecurityUser securityUser = new SecurityUser(user);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(
                        securityUser,
                        null,
                        securityUser.getAuthorities()
                );

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }
}
