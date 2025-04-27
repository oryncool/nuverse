package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfessorExperienceCreateDTO {
    private UUID id;
    private UUID professorId;
    private UUID universityId;
    private String position;
    private Integer startYear;
    private Integer endYear;
}
