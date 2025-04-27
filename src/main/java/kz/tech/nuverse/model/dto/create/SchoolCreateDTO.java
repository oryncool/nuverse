package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class SchoolCreateDTO {
    private UUID id;
    private String name;
}
