package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.UniversityDTO;
import kz.tech.nuverse.model.dto.create.UniversityCreateDTO;
import kz.tech.nuverse.model.entity.UniversityEntity;
import kz.tech.nuverse.repository.UniversityRepository;
import kz.tech.nuverse.service.UniversityService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UniversityServiceImpl implements UniversityService {

    private final UniversityRepository universityRepository;

    @Override
    public UniversityDTO createUniversity(UniversityCreateDTO universityCreateDTO) {
        UniversityEntity entity = ModelMapperUtil.map(universityCreateDTO, UniversityEntity.class);

        return ModelMapperUtil.map(universityRepository.save(entity), UniversityDTO.class);
    }

    @Override
    public UniversityDTO getUniversityById(UUID id) {
        return universityRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, UniversityDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("University not found with id " + id));
    }

    @Override
    public List<UniversityDTO> getAllUniversities() {
        return ModelMapperUtil.mapAll(universityRepository.findAll(), UniversityDTO.class);
    }

    @Override
    public UniversityDTO updateUniversity(UUID id, UniversityCreateDTO universityCreateDTO) {
        UniversityEntity existing = universityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("University not found with id " + id));

        ModelMapperUtil.map(universityCreateDTO, existing);
        return ModelMapperUtil.map(universityRepository.save(existing), UniversityDTO.class);
    }

    @Override
    public void deleteUniversity(UUID id) {
        universityRepository.deleteById(id);
    }

    @Override
    public List<UniversityDTO> searchUniversities(String keyword) {
        return ModelMapperUtil.mapAll(
                universityRepository.findByNameContainingIgnoreCaseOrCountryContainingIgnoreCase(keyword, keyword),
                UniversityDTO.class
        );
    }
}
