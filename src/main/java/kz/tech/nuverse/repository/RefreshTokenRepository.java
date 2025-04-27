package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.RefreshTokenEntity;
import kz.tech.nuverse.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);
    void deleteByUser(User user);
    Optional<RefreshTokenEntity> findByUser(User user);
}
