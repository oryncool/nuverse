package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class CourseDTO {
    private UUID id;
    private String name;
    private SchoolDTO school;
}
