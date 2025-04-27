package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "professors_experiences", schema = "nuverse")
@Data
public class ProfessorExperienceEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "professor_id", insertable=false, updatable=false)
    private ProfessorEntity professor;

    @Column(name = "professor_id")
    private UUID professorId;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private UniversityEntity university;

    private String position;
    private Integer startYear;
    private Integer endYear;
}
