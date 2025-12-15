package se.jensen.yuki.springboot.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.jensen.yuki.springboot.DTO.auth.AuthRegisterRequestDTO;
import se.jensen.yuki.springboot.DTO.auth.JwtResponseDTO;
import se.jensen.yuki.springboot.DTO.auth.LoginDTO;
import se.jensen.yuki.springboot.mapper.AuthMapper;
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
                jwtService.generateToken(registeredUser),
                "Bearer"
        );
    }

    public JwtResponseDTO login(LoginDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.email(), dto.password())
        );

        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new RuntimeException("Wrong email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Wrong email or password");
        }
        return new JwtResponseDTO(
                jwtService.generateToken(user),
                "Bearer"
        );
    }
}
