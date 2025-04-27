package kz.tech.nuverse.model.dto;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class NewsDTO {
    private UUID id;
    private BaseDictionaryDTO type;
    private UserDTO user;
    private String header;
    private String text;
}
