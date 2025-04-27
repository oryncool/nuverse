package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.ProfessorAppointmentDTO;
import kz.tech.nuverse.model.dto.create.ProfessorAppointmentCreateDTO;

import java.util.List;
import java.util.UUID;

public interface ProfessorAppointmentService {
    ProfessorAppointmentDTO create(ProfessorAppointmentCreateDTO dto);
    ProfessorAppointmentDTO getById(UUID id);
    List<ProfessorAppointmentDTO> getAll();
    ProfessorAppointmentDTO update(UUID id, ProfessorAppointmentCreateDTO dto);
    void delete(UUID id);
    List<ProfessorAppointmentDTO> findByProfessorId(UUID professorId);
}
