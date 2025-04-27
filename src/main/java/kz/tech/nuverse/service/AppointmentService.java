package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.AppointmentDTO;
import kz.tech.nuverse.model.dto.create.AppointmentCreateDTO;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentDTO createAppointment(AppointmentCreateDTO appointmentCreateDTO);
    AppointmentDTO getAppointmentById(UUID id);
    List<AppointmentDTO> getAllAppointments();
    AppointmentDTO updateAppointment(UUID id, AppointmentCreateDTO appointmentCreateDTO);
    void deleteAppointment(UUID id);
}
