package kz.tech.nuverse.controller;

import kz.tech.nuverse.model.dto.auth.JwtResponseDTO;
import kz.tech.nuverse.model.dto.auth.RefreshTokenRequestDTO;
import kz.tech.nuverse.model.dto.auth.UserLoginRequest;
import kz.tech.nuverse.model.dto.auth.UserRegistrationRequest;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.service.AuthService;
import kz.tech.nuverse.util.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        User user = userService.register(request);
        return ResponseEntity.ok("User registered successfully with ID: " + user.getId());
    }

    @PostMapping("/login")
    public JwtResponseDTO login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }

    @PostMapping("/refresh-token")
    public JwtResponseDTO refreshToken(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return userService.refreshToken(refreshTokenRequestDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        userService.logout(refreshTokenRequestDTO);
        return ResponseEntity.ok("User logged out successfully");
    }


}


