package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import kz.tech.nuverse.model.dictionary.DegreeDictionaryEntity;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "students", schema = "nuverse")
@Data
public class StudentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;


    private Integer enrollmentYear;
    private Double gpa;

    @ManyToOne
    @JoinColumn(name = "major_id", insertable = false, updatable = false)
    private MajorEntity major;

    @Column(name = "major_id")
    private UUID majorId;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private DegreeDictionaryEntity degree;

    private String imageUrl;
}
