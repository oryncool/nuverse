package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.StudentDTO;
import kz.tech.nuverse.model.dto.create.StudentCreateDTO;

import java.util.List;
import java.util.UUID;

public interface StudentService {
    StudentDTO createStudent(StudentCreateDTO studentCreateDTO);
    StudentDTO getStudentById(UUID id);
    List<StudentDTO> getAllStudents();
    StudentDTO updateStudent(UUID id, StudentCreateDTO studentCreateDTO);
    void deleteStudent(UUID id);
}
