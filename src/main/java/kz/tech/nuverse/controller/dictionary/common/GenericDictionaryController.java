package kz.tech.nuverse.controller.dictionary.common;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import kz.tech.nuverse.model.base.BaseDictionaryEntity;
import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import kz.tech.nuverse.repository.dictionary.common.BaseRepository;
import kz.tech.nuverse.repository.dictionary.common.BaseRepositoryImpl;
import kz.tech.nuverse.util.ModelMapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class GenericDictionaryController <T extends BaseDictionaryEntity, D extends BaseDictionaryDTO>{

    @PersistenceContext
    private EntityManager entityManager;

    protected BaseRepository<T> repository;

    @PostConstruct
    private void init() {
        repository = new BaseRepositoryImpl<>(JpaEntityInformationSupport.getEntityInformation(getEntityClazz(), entityManager), entityManager);
    }

    private Class<T> getEntityClazz() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @GetMapping
    public ResponseEntity<List<D>> getAll() {
        List<D> dictionary = repository.findAll()
                .stream()
                .map(x -> ModelMapperUtil.map(x, getDTOClass()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dictionary);
    }

    @GetMapping("/exists")
    public ResponseEntity<List<D>> getExists() {
        List<D> dictionary = repository.findByExistTrue()
                .stream()
                .map(this::convertToDto).sorted((o1, o2) -> o1.getId().compareTo(o2.getId()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dictionary);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<D> save(@RequestBody D dictionary) {
        T entity = ModelMapperUtil.map(dictionary, getEntityClazz());
        D dto = ModelMapperUtil.map(repository.save(entity), getDTOClass());
        return ResponseEntity.ok(dto);
    }


    protected D convertToDto(T entity) {

        return ModelMapperUtil.map(entity, getDTOClass());
    }


    private Class<D> getDTOClass() {
        return (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }
}
