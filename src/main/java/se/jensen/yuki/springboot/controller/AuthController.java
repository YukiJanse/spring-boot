package se.jensen.yuki.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.jensen.yuki.springboot.dto.auth.AuthRegisterRequestDTO;
import se.jensen.yuki.springboot.dto.auth.JwtResponseDTO;
import se.jensen.yuki.springboot.dto.auth.LoginDTO;
import se.jensen.yuki.springboot.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponseDTO> registerUser(@RequestBody AuthRegisterRequestDTO requestDTO) {
        log.info("Starting to register user");

        return ResponseEntity.ok(authService.registerUser(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginDTO dto) {
        log.info("Starting to login session");

        return ResponseEntity.ok(authService.login(dto));
    }
}
