package kz.tech.nuverse.model.dto;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class StudentDTO {
    private UUID id;
    private UserDTO user;
    private Integer enrollmentYear;
    private Double gpa;
    private MajorDTO major;
    private BaseDictionaryDTO degree;
}
