package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<EventEntity, UUID> {
    List<EventEntity> findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(String search, String location);
}