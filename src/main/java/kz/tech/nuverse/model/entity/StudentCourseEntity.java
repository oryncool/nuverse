package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "students_courses", schema = "nuverse")
@Data
public class StudentCourseEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id", insertable=false, updatable=false)
    private StudentEntity student;

    @Column(name = "student_id")
    private UUID studentId;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable=false, updatable=false)
    private CourseEntity course;

    @Column(name = "course_id")
    private UUID courseId;
}
