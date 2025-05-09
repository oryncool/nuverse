package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {

    List<CourseEntity> findByNameContainingIgnoreCase(String name);
}