package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.SchoolDTO;
import kz.tech.nuverse.model.dto.create.SchoolCreateDTO;

import java.util.List;
import java.util.UUID;

public interface SchoolService {
    SchoolDTO createSchool(SchoolCreateDTO schoolCreateDTO);
    SchoolDTO getSchoolById(UUID id);
    List<SchoolDTO> getAllSchools();
    SchoolDTO updateSchool(UUID id, SchoolCreateDTO schoolCreateDTO);
    void deleteSchool(UUID id);
    List<SchoolDTO> searchSchools(String keyword);
}
