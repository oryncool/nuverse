package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.ProfessorExperienceDTO;
import kz.tech.nuverse.model.dto.create.ProfessorExperienceCreateDTO;

import java.util.List;
import java.util.UUID;

public interface ProfessorExperienceService {
    ProfessorExperienceDTO create(ProfessorExperienceCreateDTO dto);
    ProfessorExperienceDTO getById(UUID id);
    List<ProfessorExperienceDTO> getAll();
    ProfessorExperienceDTO update(UUID id, ProfessorExperienceCreateDTO dto);
    void delete(UUID id);
    List<ProfessorExperienceDTO> findByProfessorId(UUID professorId);
}
