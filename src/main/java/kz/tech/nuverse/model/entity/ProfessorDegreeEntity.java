package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import kz.tech.nuverse.model.dictionary.DegreeDictionaryEntity;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "professors_degrees", schema = "nuverse")
@Data
public class ProfessorDegreeEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "professor_id", insertable=false, updatable=false)
    private ProfessorEntity professor;

    @Column(name = "professor_id")
    private UUID professorId;

    @ManyToOne
    @JoinColumn(name = "university_id", insertable = false, updatable = false)
    private UniversityEntity university;

    @Column(name = "university_id")
    private UUID universityId;

    @ManyToOne
    @JoinColumn(name = "degree_id")
    private DegreeDictionaryEntity degree;

    private Integer startYear;
    private Integer endYear;
}
