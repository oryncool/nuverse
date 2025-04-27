package kz.tech.nuverse.model.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Базовая модель плоского словаря с полными наименованиями
 */
@MappedSuperclass
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseDictionaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String valueEn;

    @Column(nullable = false, length = 1000)
    private String valueRu;

    @Column(length = 1000)
    private String valueKz;

    @Column(columnDefinition = "boolean default true")
    private Boolean exist = true;

    @JsonIgnore
    public String getValue() {
        return kz.tech.nuverse.util.LanguageUtils.getMessage(valueRu, valueEn, valueKz);
    }

}
