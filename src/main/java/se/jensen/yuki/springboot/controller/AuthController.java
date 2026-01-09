package se.jensen.yuki.springboot.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.yuki.springboot.dto.auth.AuthRegisterRequestDTO;
import se.jensen.yuki.springboot.dto.auth.LoginDTO;
import se.jensen.yuki.springboot.service.AuthService;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthRegisterRequestDTO requestDTO,
                                          HttpServletResponse response) {
        log.info("Starting to register user");
        // register user and generate tokens
        AuthService.TokenPair pair = authService.registerUser(requestDTO);
        // set refresh token cookie
        setRefreshTokenCookie(response, pair.refreshToken());
        return ResponseEntity.ok(Map.of("accessToken", pair.accessToken()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto,
                                   HttpServletResponse response) {
        log.info("Starting to login session");
        // authenticate user and generate tokens
        AuthService.TokenPair pair = authService.login(dto);
        // set refresh token cookie
        setRefreshTokenCookie(response, pair.refreshToken());
        return ResponseEntity.ok(Map.of("accessToken", pair.accessToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken,
                                     HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(401).build();
        }

        // refresh access token
        Optional<AuthService.TokenPair> maybe = authService.refreshAccessToken(refreshToken);
        // invalid refresh token
        if (maybe.isEmpty()) {
            // clear cookie
            ResponseCookie clear = ResponseCookie.from("refreshToken", "")
                    .path("/")
                    .httpOnly(true)
                    .maxAge(0)
                    .build();
            response.addHeader("Set-Cookie", clear.toString());
            return ResponseEntity.status(401).build();
        }

        AuthService.TokenPair pair = maybe.get();
        // set new refresh token cookie
        setRefreshTokenCookie(response, pair.refreshToken());

        return ResponseEntity.ok(Map.of("accessToken", pair.accessToken()));
    }

    private void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(false) // set to true in production
                .path("/")
                .maxAge(60L * 60 * 24 * 30)
                .sameSite("Strict")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
