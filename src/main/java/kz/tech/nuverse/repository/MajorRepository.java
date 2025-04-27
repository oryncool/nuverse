package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.MajorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MajorRepository extends JpaRepository<MajorEntity, UUID> {

    List<MajorEntity> findByNameContainingIgnoreCase(String name);
}

