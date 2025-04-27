package kz.tech.nuverse.repository.dictionary.common;

import jakarta.persistence.EntityManager;
import kz.tech.nuverse.model.base.BaseDictionaryEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;


public class BaseRepositoryImpl<T extends BaseDictionaryEntity> extends SimpleJpaRepository<T, Long> implements BaseRepository<T> {

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

    }

    public List<T> findByExistTrue() {

        return getQuery(Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("exist"),true)), getDomainClass(), Sort.unsorted()).getResultList();

    }
}
