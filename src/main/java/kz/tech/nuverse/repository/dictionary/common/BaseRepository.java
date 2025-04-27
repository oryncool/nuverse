package kz.tech.nuverse.repository.dictionary.common;

import kz.tech.nuverse.model.base.BaseDictionaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends BaseDictionaryEntity> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

    List<T> findByExistTrue();
}
