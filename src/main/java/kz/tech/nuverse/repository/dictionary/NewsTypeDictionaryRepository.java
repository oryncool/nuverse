package kz.tech.nuverse.repository.dictionary;

import kz.tech.nuverse.model.dictionary.NewsTypeDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsTypeDictionaryRepository extends JpaRepository<NewsTypeDictionaryEntity, Long> {
}
