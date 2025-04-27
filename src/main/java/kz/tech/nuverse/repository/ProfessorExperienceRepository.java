package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.ProfessorExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfessorExperienceRepository extends JpaRepository<ProfessorExperienceEntity, UUID> {

    List<ProfessorExperienceEntity> findByProfessorId(UUID professorId);
}