package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfessorExperienceDTO {
    private UUID id;
    private ProfessorDTO professor;
    private UniversityDTO university;
    private String position;
    private Integer startYear;
    private Integer endYear;
}
