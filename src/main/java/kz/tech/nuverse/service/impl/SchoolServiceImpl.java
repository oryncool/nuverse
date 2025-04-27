package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.SchoolDTO;
import kz.tech.nuverse.model.dto.create.SchoolCreateDTO;
import kz.tech.nuverse.model.entity.SchoolEntity;
import kz.tech.nuverse.repository.SchoolRepository;
import kz.tech.nuverse.service.SchoolService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Override
    public SchoolDTO createSchool(SchoolCreateDTO schoolCreateDTO) {
        SchoolEntity entity = ModelMapperUtil.map(schoolCreateDTO, SchoolEntity.class);

        return ModelMapperUtil.map(schoolRepository.save(entity), SchoolDTO.class);
    }

    @Override
    public SchoolDTO getSchoolById(UUID id) {
        return schoolRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, SchoolDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("School not found with id " + id));
    }

    @Override
    public List<SchoolDTO> getAllSchools() {
        return ModelMapperUtil.mapAll(schoolRepository.findAll(), SchoolDTO.class);
    }

    @Override
    public SchoolDTO updateSchool(UUID id, SchoolCreateDTO schoolCreateDTO) {
        SchoolEntity existing = schoolRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("School not found with id " + id));

        ModelMapperUtil.map(schoolCreateDTO, existing);
        return ModelMapperUtil.map(schoolRepository.save(existing), SchoolDTO.class);
    }

    @Override
    public void deleteSchool(UUID id) {
        schoolRepository.deleteById(id);
    }

    @Override
    public List<SchoolDTO> searchSchools(String keyword) {
        return ModelMapperUtil.mapAll(
                schoolRepository.findByNameContainingIgnoreCase(keyword),
                SchoolDTO.class
        );
    }
}
