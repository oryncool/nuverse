package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UniversityRepository extends JpaRepository<UniversityEntity, UUID> {

    List<UniversityEntity> findByNameContainingIgnoreCaseOrCountryContainingIgnoreCase(String search, String location);
}