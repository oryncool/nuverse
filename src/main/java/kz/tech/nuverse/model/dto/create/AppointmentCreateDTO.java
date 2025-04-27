package kz.tech.nuverse.model.dto.create;

import kz.tech.nuverse.model.dto.dictionary.WeekDictionaryDTO;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Data
@Builder
public class AppointmentCreateDTO {
    private BaseDictionaryDTO week;
    private LocalTime startTime;
    private LocalTime endTime;
}
