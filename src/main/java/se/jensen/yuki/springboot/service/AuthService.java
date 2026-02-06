package se.jensen.yuki.springboot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.dto.auth.AuthRegisterRequestDTO;
import se.jensen.yuki.springboot.dto.auth.LoginDTO;
import se.jensen.yuki.springboot.exception.UnauthorizedException;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.model.RefreshToken;
import se.jensen.yuki.springboot.model.SecurityUser;
import se.jensen.yuki.springboot.repository.RefreshTokenRepository;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserJpaEntity;
import se.jensen.yuki.springboot.user.infrastructure.persistence.UserRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    private final long refreshTokenDurationSeconds = 60L * 60 * 24 * 30; // 30 days

    public RefreshToken createRefreshToken(UserJpaEntity user) {
        RefreshToken rt = RefreshToken.builder()
                .user(user)
                .token(generateTokenValue())
                .expiresAt(Instant.now().plusSeconds(refreshTokenDurationSeconds))
                .revoked(false)
                .build();
        return refreshTokenRepository.save(rt);
    }

    public Optional<TokenPair> refreshAccessToken(String refreshTokenValue) {
        Optional<RefreshToken> found = refreshTokenRepository.findByToken(refreshTokenValue);
        if (found.isEmpty()) {
            return Optional.empty();
        }

        RefreshToken rt = found.get();
        if (rt.isRevoked() || rt.getExpiresAt().isBefore(Instant.now())) {
            // revoke/clean up the token
            rt.setRevoked(true);
            refreshTokenRepository.save(rt);
            return Optional.empty();
        }

        UserJpaEntity user = rt.getUser();
        // rotation: revoke old token and create a new one
        rt.setRevoked(true);
        refreshTokenRepository.save(rt);

        RefreshToken newRt = createRefreshToken(user);
        String newAccessToken = jwtService.generateAccessToken(user.getId());

        return Optional.of(new TokenPair(newAccessToken, newRt.getToken()));
    }

    public void revokeAllForUser(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    private String generateTokenValue() {
        return UUID.randomUUID().toString() + "-" + UUID.randomUUID();
    }

    @Transactional
    public TokenPair registerUser(AuthRegisterRequestDTO dto) {
        UserJpaEntity user = UserJpaEntity.builder()
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .displayName(dto.username())
                .bio("Hello World!")
                .role("USER")
                .build();

        UserJpaEntity registeredUser = userRepository.save(user);
        String access = jwtService.generateAccessToken(registeredUser.getId());
        RefreshToken rt = createRefreshToken(registeredUser);

        return new TokenPair(access, rt.getToken());
    }

    public TokenPair login(LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
        UserJpaEntity user = userRepository.findById(securityUser.getId())
                .orElseThrow(() -> new UserNotFoundException("No user found"));
        String access = jwtService.generateAccessToken(securityUser.getId());
        RefreshToken rt = createRefreshToken(user);

        return new TokenPair(access, rt.getToken());
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UnauthorizedException("You need to login");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof SecurityUser securityUser) {
            return securityUser.getId();
        }

        throw new IllegalStateException("Invalid principal");
    }

    public void checkCurrentPassword(String currentPassword) {
        Long userId = getCurrentUserId();
        UserJpaEntity currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found"));
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
    }

    public record TokenPair(String accessToken, String refreshToken) {
    }
}
