package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.StudentCourseDTO;
import kz.tech.nuverse.model.dto.create.StudentCourseCreateDTO;
import kz.tech.nuverse.model.entity.StudentCourseEntity;
import kz.tech.nuverse.repository.StudentCourseRepository;
import kz.tech.nuverse.service.StudentCourseService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {

    private final StudentCourseRepository repository;

    @Override
    public StudentCourseDTO create(StudentCourseCreateDTO dto) {
        StudentCourseEntity entity = ModelMapperUtil.map(dto, StudentCourseEntity.class);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, StudentCourseDTO.class);
    }

    @Override
    public StudentCourseDTO getById(UUID id) {
        StudentCourseEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentCourse not found with id: " + id));
        return ModelMapperUtil.map(entity, StudentCourseDTO.class);
    }

    @Override
    public List<StudentCourseDTO> getAll() {
        return ModelMapperUtil.mapAll(repository.findAll(), StudentCourseDTO.class);
    }

    @Override
    public StudentCourseDTO update(UUID id, StudentCourseCreateDTO dto) {
        StudentCourseEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("StudentCourse not found with id: " + id));
        ModelMapperUtil.map(dto, entity);
        entity = repository.save(entity);
        return ModelMapperUtil.map(entity, StudentCourseDTO.class);
    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Override
    public List<StudentCourseDTO> findByStudentId(UUID studentId) {
        return ModelMapperUtil.mapAll(repository.findByStudentId(studentId), StudentCourseDTO.class);
    }
}
