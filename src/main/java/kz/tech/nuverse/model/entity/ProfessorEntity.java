package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "professors", schema = "nuverse")
@Data
public class ProfessorEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    private User user;

    @Column(name = "user_id")
    private UUID userId;

    private String office;
    private String researchInterest;
    private String imageUrl;
}
