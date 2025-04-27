package kz.tech.nuverse.model.dto.dictionary.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@Data
public class BaseDictionaryDTO {

    private Long id;
    private String valueEn;
    private String valueRu;
    private String valueKz;
    private Boolean exist;
}
