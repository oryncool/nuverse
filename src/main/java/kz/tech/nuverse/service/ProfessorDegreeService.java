package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.ProfessorDegreeDTO;
import kz.tech.nuverse.model.dto.create.ProfessorDegreeCreateDTO;

import java.util.List;
import java.util.UUID;

public interface ProfessorDegreeService {
    ProfessorDegreeDTO create(ProfessorDegreeCreateDTO dto);
    ProfessorDegreeDTO getById(UUID id);
    List<ProfessorDegreeDTO> getAll();
    ProfessorDegreeDTO update(UUID id, ProfessorDegreeCreateDTO dto);
    void delete(UUID id);
    List<ProfessorDegreeDTO> findByProfessorId(UUID professorId);
}
