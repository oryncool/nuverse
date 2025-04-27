package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import kz.tech.nuverse.model.dictionary.NewsTypeDictionaryEntity;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "news", schema = "nuverse")
@Data
public class NewsEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private NewsTypeDictionaryEntity type;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;

    private String header;
    @Column(columnDefinition = "TEXT")
    private String text;
    private String imageUrl;
}

