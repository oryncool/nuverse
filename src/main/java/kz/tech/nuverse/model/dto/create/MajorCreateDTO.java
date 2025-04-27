package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class MajorCreateDTO {
    private String name;
    private UUID schoolId;
}
