package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProfessorCreateDTO {
    private UUID userId;
    private String office;
    private String researchInterest;
}
