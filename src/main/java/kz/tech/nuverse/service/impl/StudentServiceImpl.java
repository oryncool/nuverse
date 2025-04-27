package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.StudentDTO;
import kz.tech.nuverse.model.dto.create.StudentCreateDTO;
import kz.tech.nuverse.model.entity.StudentEntity;
import kz.tech.nuverse.repository.StudentRepository;
import kz.tech.nuverse.service.StudentService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentCreateDTO studentCreateDTO) {
        StudentEntity entity = ModelMapperUtil.map(studentCreateDTO, StudentEntity.class);

        return ModelMapperUtil.map(studentRepository.save(entity), StudentDTO.class);
    }

    @Override
    public StudentDTO getStudentById(UUID id) {
        return studentRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, StudentDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return ModelMapperUtil.mapAll(studentRepository.findAll(), StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(UUID id, StudentCreateDTO studentCreateDTO) {
        StudentEntity existing = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id " + id));

        ModelMapperUtil.map(studentCreateDTO, existing);
        return ModelMapperUtil.map(studentRepository.save(existing), StudentDTO.class);
    }

    @Override
    public void deleteStudent(UUID id) {
        studentRepository.deleteById(id);
    }
}
