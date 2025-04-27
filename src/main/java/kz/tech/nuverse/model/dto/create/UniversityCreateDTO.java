package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UniversityCreateDTO {
    private UUID id;
    private String name;
    private String country;
}
