package kz.tech.nuverse.service.impl;

import kz.tech.nuverse.model.entity.RefreshTokenEntity;
import kz.tech.nuverse.model.entity.User;
import kz.tech.nuverse.repository.RefreshTokenRepository;
import kz.tech.nuverse.repository.UserRepository;
import kz.tech.nuverse.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Value("${refresh.token.expiry}")
    private long refreshTokenExpiry;
    @Override
    public RefreshTokenEntity createRefreshToken(User user) {
        RefreshTokenEntity refreshToken = findByUser(user);

        if (refreshToken != null) {
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiry));
        } else {
            refreshToken = RefreshTokenEntity.builder()
                    .user(userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found")))
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(refreshTokenExpiry)) // 7 дней
                    .build();
        }
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshTokenEntity> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
        if(token.getExpiryDate().isBefore(Instant.now())){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token is expired");
        }
        return token;
    }

    @Override
    public RefreshTokenEntity findByUser(User user) {
        return refreshTokenRepository.findByUser(user).orElse(null);
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}
