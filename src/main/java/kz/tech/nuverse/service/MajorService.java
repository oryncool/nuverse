package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.MajorDTO;
import kz.tech.nuverse.model.dto.create.MajorCreateDTO;

import java.util.List;
import java.util.UUID;

public interface MajorService {
    MajorDTO createMajor(MajorCreateDTO majorCreateDTO);
    MajorDTO getMajorById(UUID id);
    List<MajorDTO> getAllMajors();
    MajorDTO updateMajor(UUID id, MajorCreateDTO majorCreateDTO);
    void deleteMajor(UUID id);
    List<MajorDTO> searchMajors(String keyword);
}
