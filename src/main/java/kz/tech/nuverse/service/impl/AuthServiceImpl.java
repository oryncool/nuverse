package kz.tech.nuverse.service.impl;

import jakarta.transaction.Transactional;
import kz.tech.nuverse.model.dictionary.RoleDictionaryEntity;
import kz.tech.nuverse.model.dto.auth.JwtResponseDTO;
import kz.tech.nuverse.model.dto.auth.RefreshTokenRequestDTO;
import kz.tech.nuverse.model.dto.auth.UserLoginRequest;
import kz.tech.nuverse.model.dto.auth.UserRegistrationRequest;
import kz.tech.nuverse.model.entity.RefreshTokenEntity;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.repository.UserRepository;
import kz.tech.nuverse.repository.dictionary.RoleDictionaryRepository;
import kz.tech.nuverse.security.JwtTokenProvider;
import kz.tech.nuverse.service.AuthService;
import kz.tech.nuverse.service.RefreshTokenService;
import kz.tech.nuverse.util.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RoleDictionaryRepository roleRepository;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public User register(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalStateException("User with username phone already exists");
        }

        RoleDictionaryEntity role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalStateException("Role not found"));

        User user = new User();
        user.setName(request.getName());
        user.setLastName(request.getLastName());
        user.setSurname(request.getSurname());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setBirthday(request.getBirthday());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Хэшируем пароль
        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public JwtResponseDTO login(UserLoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalStateException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("Invalid credentials");
        }

        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user);
        return JwtResponseDTO.builder()
                .accessToken(jwtTokenProvider.generateToken(user.getUsername()))
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    @Transactional
    public JwtResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshTokenEntity::getUser)
                .map(user -> {
                    String accessToken = jwtTokenProvider.generateToken(user.getUsername());
                    return JwtResponseDTO.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
                }).orElseThrow(() ->new RuntimeException("Invalid refresh token"));
    }

    @Override
    @Transactional
    public void logout(RefreshTokenRequestDTO refreshTokenRequestDTO) {
        RefreshTokenEntity refreshToken = refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        refreshTokenService.deleteByUser(refreshToken.getUser());
    }
}
