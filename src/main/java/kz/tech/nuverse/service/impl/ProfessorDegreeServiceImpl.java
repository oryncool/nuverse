package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.ProfessorDegreeDTO;
import kz.tech.nuverse.model.dto.create.ProfessorDegreeCreateDTO;
import kz.tech.nuverse.model.entity.ProfessorDegreeEntity;
import kz.tech.nuverse.repository.ProfessorDegreeRepository;
import kz.tech.nuverse.service.ProfessorDegreeService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorDegreeServiceImpl implements ProfessorDegreeService {

    private final ProfessorDegreeRepository repository;

    @Override
    public ProfessorDegreeDTO create(ProfessorDegreeCreateDTO dto) {
        ProfessorDegreeEntity entity = ModelMapperUtil.map(dto, ProfessorDegreeEntity.class);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, ProfessorDegreeDTO.class);
    }

    @Override
    public ProfessorDegreeDTO getById(UUID id) {
        ProfessorDegreeEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfessorDegree not found with id: " + id));
        return ModelMapperUtil.map(entity, ProfessorDegreeDTO.class);
    }

    @Override
    public List<ProfessorDegreeDTO> getAll() {
        return ModelMapperUtil.mapAll(repository.findAll(), ProfessorDegreeDTO.class);
    }

    @Override
    public ProfessorDegreeDTO update(UUID id, ProfessorDegreeCreateDTO dto) {
        ProfessorDegreeEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfessorDegree not found with id: " + id));
        ModelMapperUtil.map(dto, entity);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, ProfessorDegreeDTO.class);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProfessorDegreeDTO> findByProfessorId(UUID professorId) {
        return ModelMapperUtil.mapAll(repository.findByProfessorId(professorId), ProfessorDegreeDTO.class);
    }
}
