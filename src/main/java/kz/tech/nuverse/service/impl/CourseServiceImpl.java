package kz.tech.nuverse.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.tech.nuverse.model.dto.CourseDTO;
import kz.tech.nuverse.model.dto.create.CourseCreateDTO;
import kz.tech.nuverse.model.entity.CourseEntity;
import kz.tech.nuverse.repository.CourseRepository;
import kz.tech.nuverse.service.CourseService;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseDTO createCourse(CourseCreateDTO courseCreateDTO) {
        CourseEntity entity = ModelMapperUtil.map(courseCreateDTO, CourseEntity.class);
        return ModelMapperUtil.map(courseRepository.save(entity), CourseDTO.class);
    }

    @Override
    public CourseDTO getCourseById(UUID id) {
        return courseRepository.findById(id)
                .map(e -> ModelMapperUtil.map(e, CourseDTO.class))
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return ModelMapperUtil.mapAll(courseRepository.findAll(), CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(UUID id, CourseCreateDTO courseCreateDTO) {
        CourseEntity existing = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id " + id));

        ModelMapperUtil.map(courseCreateDTO, existing);
        return ModelMapperUtil.map(courseRepository.save(existing), CourseDTO.class);
    }

    @Override
    public void deleteCourse(UUID id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseDTO> searchCourses(String keyword) {
        return ModelMapperUtil.mapAll(
                courseRepository.findByNameContainingIgnoreCase(keyword),
                CourseDTO.class
        );
    }
}
