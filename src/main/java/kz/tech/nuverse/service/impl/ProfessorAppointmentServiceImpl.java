package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.ProfessorAppointmentDTO;
import kz.tech.nuverse.model.dto.create.ProfessorAppointmentCreateDTO;
import kz.tech.nuverse.model.entity.ProfessorAppointmentEntity;
import kz.tech.nuverse.repository.ProfessorAppointmentRepository;
import kz.tech.nuverse.service.ProfessorAppointmentService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorAppointmentServiceImpl implements ProfessorAppointmentService {

    private final ProfessorAppointmentRepository repository;

    @Override
    public ProfessorAppointmentDTO create(ProfessorAppointmentCreateDTO dto) {
        ProfessorAppointmentEntity entity = ModelMapperUtil.map(dto, ProfessorAppointmentEntity.class);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, ProfessorAppointmentDTO.class);
    }

    @Override
    public ProfessorAppointmentDTO getById(UUID id) {
        ProfessorAppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfessorAppointment not found with id: " + id));
        return ModelMapperUtil.map(entity, ProfessorAppointmentDTO.class);
    }

    @Override
    public List<ProfessorAppointmentDTO> getAll() {
        return ModelMapperUtil.mapAll(repository.findAll(), ProfessorAppointmentDTO.class);
    }

    @Override
    public ProfessorAppointmentDTO update(UUID id, ProfessorAppointmentCreateDTO dto) {
        ProfessorAppointmentEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProfessorAppointment not found with id: " + id));
        ModelMapperUtil.map(dto, entity);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, ProfessorAppointmentDTO.class);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<ProfessorAppointmentDTO> findByProfessorId(UUID professorId) {
        return ModelMapperUtil.mapAll(repository.findByProfessorId(professorId), ProfessorAppointmentDTO.class);
    }
}
