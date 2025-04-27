package kz.tech.nuverse.model.dto.create;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class ProfessorDegreeCreateDTO {
    private UUID professorId;
    private UUID universityId;
    private BaseDictionaryDTO degree;
    private Integer startYear;
    private Integer endYear;
}
