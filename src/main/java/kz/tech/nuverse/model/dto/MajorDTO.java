package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class MajorDTO {
    private UUID id;
    private String name;
    private SchoolDTO school;
}
