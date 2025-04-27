package kz.tech.nuverse.model.dto.create;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class StudentCourseCreateDTO {
    private UUID id;
    private UUID studentId;
    private UUID courseId;
}
