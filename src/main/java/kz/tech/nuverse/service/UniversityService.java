package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.UniversityDTO;
import kz.tech.nuverse.model.dto.create.UniversityCreateDTO;

import java.util.List;
import java.util.UUID;

public interface UniversityService {
    UniversityDTO createUniversity(UniversityCreateDTO universityCreateDTO);
    UniversityDTO getUniversityById(UUID id);
    List<UniversityDTO> getAllUniversities();
    UniversityDTO updateUniversity(UUID id, UniversityCreateDTO universityCreateDTO);
    void deleteUniversity(UUID id);
    List<UniversityDTO> searchUniversities(String keyword);
}
