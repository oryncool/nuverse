package kz.tech.nuverse.model.dto;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class ProfessorDegreeDTO {
    private UUID id;
    private ProfessorDTO professor;
    private UniversityDTO university;
    private BaseDictionaryDTO degree;
    private Integer startYear;
    private Integer endYear;
}
