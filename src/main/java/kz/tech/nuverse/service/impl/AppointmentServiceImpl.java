package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.AppointmentDTO;
import kz.tech.nuverse.model.dto.create.AppointmentCreateDTO;
import kz.tech.nuverse.model.entity.AppointmentEntity;
import kz.tech.nuverse.repository.AppointmentRepository;
import kz.tech.nuverse.service.AppointmentService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Override
    public AppointmentDTO createAppointment(AppointmentCreateDTO appointmentCreateDTO) {
        AppointmentEntity entity = ModelMapperUtil.map(appointmentCreateDTO, AppointmentEntity.class);
        return ModelMapperUtil.map(appointmentRepository.save(entity), AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO getAppointmentById(UUID id) {
        return appointmentRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, AppointmentDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + id));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        return ModelMapperUtil.mapAll(appointmentRepository.findAll(), AppointmentDTO.class);
    }

    @Override
    public AppointmentDTO updateAppointment(UUID id, AppointmentCreateDTO appointmentCreateDTO) {
        AppointmentEntity existing = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found with id " + id));

        ModelMapperUtil.map(appointmentCreateDTO, existing);
        return ModelMapperUtil.map(appointmentRepository.save(existing), AppointmentDTO.class);
    }

    @Override
    public void deleteAppointment(UUID id) {
        appointmentRepository.deleteById(id);
    }
}
