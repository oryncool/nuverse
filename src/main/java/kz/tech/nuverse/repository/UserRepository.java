package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.EventEntity;
import kz.tech.nuverse.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    List<User> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String surname, String lastName);

    boolean existsByUsername(String username);

    List<User> findAllByRoleId(Long roleId);
}
