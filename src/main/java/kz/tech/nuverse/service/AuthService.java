package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.auth.JwtResponseDTO;
import kz.tech.nuverse.model.dto.auth.RefreshTokenRequestDTO;
import kz.tech.nuverse.model.dto.auth.UserLoginRequest;
import kz.tech.nuverse.model.dto.auth.UserRegistrationRequest;
import kz.tech.nuverse.model.entity.User;

public interface AuthService {

    User register(UserRegistrationRequest request);

    JwtResponseDTO login(UserLoginRequest request);

    JwtResponseDTO refreshToken(RefreshTokenRequestDTO refreshTokenRequestDTO);

    void logout(RefreshTokenRequestDTO refreshTokenRequestDTO);


}
