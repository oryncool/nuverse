package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.StudentCourseDTO;
import kz.tech.nuverse.model.dto.create.StudentCourseCreateDTO;

import java.util.List;
import java.util.UUID;

public interface StudentCourseService {
    StudentCourseDTO create(StudentCourseCreateDTO dto);
    StudentCourseDTO getById(UUID id);
    List<StudentCourseDTO> getAll();
    StudentCourseDTO update(UUID id, StudentCourseCreateDTO dto);
    void delete(UUID id);
    List<StudentCourseDTO> findByStudentId(UUID studentId);
}
