package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfessorAppointmentDTO {
    private UUID id;
    private ProfessorDTO professor;
    private AppointmentDTO appointment;
}
