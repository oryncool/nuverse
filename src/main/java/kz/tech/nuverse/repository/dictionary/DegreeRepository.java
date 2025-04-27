package kz.tech.nuverse.repository.dictionary;

import kz.tech.nuverse.model.dictionary.DegreeDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegreeRepository extends JpaRepository<DegreeDictionaryEntity, Long> {}