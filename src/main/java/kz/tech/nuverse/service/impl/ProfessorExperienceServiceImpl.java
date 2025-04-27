package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.ProfessorExperienceDTO;
import kz.tech.nuverse.model.dto.create.ProfessorExperienceCreateDTO;
import kz.tech.nuverse.model.entity.ProfessorExperienceEntity;
import kz.tech.nuverse.repository.ProfessorExperienceRepository;
import kz.tech.nuverse.service.ProfessorExperienceService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorExperienceServiceImpl implements ProfessorExperienceService {

    private final ProfessorExperienceRepository repository;

    @Override
    public ProfessorExperienceDTO create(ProfessorExperienceCreateDTO dto) {
        ProfessorExperienceEntity entity = ModelMapperUtil.map(dto, ProfessorExperienceEntity.class);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, ProfessorExperienceDTO.class);
    }

    @Override
    public ProfessorExperienceDTO getById(UUID id) {
        ProfessorExperienceEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfessorExperience not found with id: " + id));
        return ModelMapperUtil.map(entity, ProfessorExperienceDTO.class);
    }

    @Override
    public List<ProfessorExperienceDTO> getAll() {
        return ModelMapperUtil.mapAll(repository.findAll(), ProfessorExperienceDTO.class);
    }

    @Override
    public ProfessorExperienceDTO update(UUID id, ProfessorExperienceCreateDTO dto) {
        ProfessorExperienceEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfessorExperience not found with id: " + id));
        ModelMapperUtil.map(dto, entity);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, ProfessorExperienceDTO.class);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProfessorExperienceDTO> findByProfessorId(UUID professorId) {
        return ModelMapperUtil.mapAll(repository.findByProfessorId(professorId), ProfessorExperienceDTO.class);
    }
}
