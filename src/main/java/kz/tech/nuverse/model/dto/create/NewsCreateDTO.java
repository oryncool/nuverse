package kz.tech.nuverse.model.dto.create;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class NewsCreateDTO {
    private BaseDictionaryDTO type;
    private String header;
    private String text;
}
