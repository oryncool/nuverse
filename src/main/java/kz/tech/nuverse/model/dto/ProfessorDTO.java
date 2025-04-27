package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfessorDTO {
    private UUID id;
    private UserDTO user;
    private String office;
    private String researchInterest;
}
