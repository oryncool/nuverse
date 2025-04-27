package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "majors", schema = "nuverse")
@Data
public class MajorEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id", insertable=false, updatable=false)
    private SchoolEntity school;

    @Column(name = "school_id")
    private UUID schoolId;
}
