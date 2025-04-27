package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SchoolRepository extends JpaRepository<SchoolEntity, UUID> {

    List<SchoolEntity> findByNameContainingIgnoreCase(String name);
}