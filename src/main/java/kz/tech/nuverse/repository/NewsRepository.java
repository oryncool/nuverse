package kz.tech.nuverse.repository;

import kz.tech.nuverse.model.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NewsRepository extends JpaRepository<NewsEntity, UUID> {
    List<NewsEntity> findByHeaderContainingIgnoreCaseOrTextContainingIgnoreCase(String header, String text);

}