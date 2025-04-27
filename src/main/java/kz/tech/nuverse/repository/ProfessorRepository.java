package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, UUID> {

    List<ProfessorEntity> findByOfficeContainingIgnoreCase(String name);
}