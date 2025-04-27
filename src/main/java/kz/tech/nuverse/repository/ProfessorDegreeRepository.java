package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.ProfessorAppointmentEntity;
import kz.tech.nuverse.model.entity.ProfessorDegreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProfessorDegreeRepository extends JpaRepository<ProfessorDegreeEntity, UUID> {

    List<ProfessorDegreeEntity> findByProfessorId(UUID professorId);
}