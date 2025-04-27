package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudentCourseDTO {
    private UUID id;
    private StudentDTO student;
    private CourseDTO course;
}
