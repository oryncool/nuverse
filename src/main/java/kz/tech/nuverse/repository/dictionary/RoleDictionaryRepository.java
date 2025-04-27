package kz.tech.nuverse.repository.dictionary;

import kz.tech.nuverse.model.dictionary.RoleDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDictionaryRepository extends JpaRepository<RoleDictionaryEntity, Long> {
}
