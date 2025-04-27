package kz.tech.nuverse.model.dto.create;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudentCreateDTO {
    private UUID id;
    private UUID userId;
    private Integer enrollmentYear;
    private Double gpa;
    private MajorCreateDTO major;
    private BaseDictionaryDTO degree;
}
