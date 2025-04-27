package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import kz.tech.nuverse.model.dictionary.EventTypeDictionaryEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "events", schema = "nuverse")
@Data
public class EventEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EventTypeDictionaryEntity type;

    private String name;
    private String location;
    private LocalDateTime eventDatetime;
}
