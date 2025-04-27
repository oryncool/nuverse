package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.ProfessorDTO;
import kz.tech.nuverse.model.dto.create.ProfessorCreateDTO;
import kz.tech.nuverse.model.entity.ProfessorEntity;
import kz.tech.nuverse.repository.ProfessorRepository;
import kz.tech.nuverse.service.ProfessorService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;

    @Override
    public ProfessorDTO createProfessor(ProfessorCreateDTO professorCreateDTO) {
        ProfessorEntity entity = ModelMapperUtil.map(professorCreateDTO, ProfessorEntity.class);

        return ModelMapperUtil.map(professorRepository.save(entity), ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO getProfessorById(UUID id) {
        return professorRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, ProfessorDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Professor not found with id " + id));
    }

    @Override
    public List<ProfessorDTO> getAllProfessors() {
        return ModelMapperUtil.mapAll(professorRepository.findAll(), ProfessorDTO.class);
    }

    @Override
    public ProfessorDTO updateProfessor(UUID id, ProfessorCreateDTO professorCreateDTO) {
        ProfessorEntity existing = professorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professor not found with id " + id));

        ModelMapperUtil.map(professorCreateDTO, existing);
        return ModelMapperUtil.map(professorRepository.save(existing), ProfessorDTO.class);
    }

    @Override
    public void deleteProfessor(UUID id) {
        professorRepository.deleteById(id);
    }

    @Override
    public List<ProfessorDTO> searchProfessors(String keyword) {
        return ModelMapperUtil.mapAll(
                professorRepository.findByOfficeContainingIgnoreCase(keyword),
                ProfessorDTO.class
        );
    }
}
