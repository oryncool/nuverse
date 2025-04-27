package kz.tech.nuverse.repository.dictionary;

import kz.tech.nuverse.model.dictionary.EventTypeDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventsTypeRepository extends JpaRepository<EventTypeDictionaryEntity, Long> {}