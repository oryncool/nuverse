package kz.tech.nuverse.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "universities", schema = "nuverse")
@Data
public class UniversityEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;
    private String country;
}
