package kz.tech.nuverse.model.dictionary;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import kz.tech.nuverse.model.base.BaseDictionaryEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "news_types")
@Table(schema = "dictionary")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class NewsTypeDictionaryEntity extends BaseDictionaryEntity {
}
