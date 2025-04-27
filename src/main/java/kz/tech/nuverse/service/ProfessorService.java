package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.create.ProfessorCreateDTO;

import java.util.List;
import java.util.UUID;

public interface ProfessorService {
    ProfessorDTO createProfessor(ProfessorCreateDTO professorCreateDTO);
    ProfessorDTO getProfessorById(UUID id);
    List<ProfessorDTO> getAllProfessors();
    ProfessorDTO updateProfessor(UUID id, ProfessorCreateDTO professorCreateDTO);
    void deleteProfessor(UUID id);
    List<ProfessorDTO> searchProfessors(String keyword);
}
