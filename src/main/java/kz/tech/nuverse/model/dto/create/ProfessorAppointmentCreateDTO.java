package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfessorAppointmentCreateDTO {
    private UUID professorId;
    private UUID appointmentId;
}
