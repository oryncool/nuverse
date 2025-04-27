package kz.tech.nuverse.service;

import kz.tech.nuverse.model.dto.CourseDTO;
import kz.tech.nuverse.model.dto.create.CourseCreateDTO;

import java.util.List;
import java.util.UUID;

public interface CourseService {
    CourseDTO createCourse(CourseCreateDTO courseCreateDTO);
    CourseDTO getCourseById(UUID id);
    List<CourseDTO> getAllCourses();
    CourseDTO updateCourse(UUID id, CourseCreateDTO courseCreateDTO);
    void deleteCourse(UUID id);
    List<CourseDTO> searchCourses(String keyword);
}
