package kz.tech.nuverse.model.dto;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data

public class EventDTO {
    private UUID id;
    private BaseDictionaryDTO type;
    private String name;
    private String location;
    private LocalDateTime eventDatetime;
}
