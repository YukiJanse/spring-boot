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
import se.jensen.yuki.springboot.dto.auth.JwtResponseDTO;
import se.jensen.yuki.springboot.dto.auth.LoginDTO;
import se.jensen.yuki.springboot.exception.UnauthorizedException;
import se.jensen.yuki.springboot.exception.UserNotFoundException;
import se.jensen.yuki.springboot.mapper.AuthMapper;
import se.jensen.yuki.springboot.model.SecurityUser;
import se.jensen.yuki.springboot.model.User;
import se.jensen.yuki.springboot.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthMapper authMapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public JwtResponseDTO registerUser(AuthRegisterRequestDTO dto) {
        //User user = authMapper.registerDtoToUser(dto);
        User user = User.builder()
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .displayName(dto.username())
                .bio("Hello World!")
                .role("USER")
                .build();

        User registeredUser = userRepository.save(user);

        return new JwtResponseDTO(
                jwtService.generateToken(registeredUser.getId()),
                "Bearer"
        );
    }

    public JwtResponseDTO login(LoginDTO dto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();

        return new JwtResponseDTO(
                jwtService.generateToken(securityUser.getId()),
                "Bearer"
        );
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
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found"));
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new BadCredentialsException("Wrong password");
        }
    }
}
