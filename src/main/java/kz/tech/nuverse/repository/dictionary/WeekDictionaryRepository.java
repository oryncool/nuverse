package kz.tech.nuverse.repository.dictionary;

import kz.tech.nuverse.model.dictionary.WeekDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeekDictionaryRepository extends JpaRepository<WeekDictionaryEntity, Long> {
}
