package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.StudentCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StudentCourseRepository extends JpaRepository<StudentCourseEntity, UUID> {

    List<StudentCourseEntity> findByStudentId(UUID studentId);
}