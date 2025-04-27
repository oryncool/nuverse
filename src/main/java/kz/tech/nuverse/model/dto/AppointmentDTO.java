package kz.tech.nuverse.model.dto;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class AppointmentDTO {
    private UUID id;
    private BaseDictionaryDTO week;
    private LocalTime startTime;
    private LocalTime endTime;
}
