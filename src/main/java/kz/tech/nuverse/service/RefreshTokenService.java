package kz.tech.nuverse.service;

import kz.tech.nuverse.model.entity.RefreshTokenEntity;
import kz.tech.nuverse.model.entity.User;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshTokenEntity createRefreshToken(User user);

    Optional<RefreshTokenEntity> findByToken(String token);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken);

    RefreshTokenEntity findByUser(User user);

    void deleteByUser(User user);
}
