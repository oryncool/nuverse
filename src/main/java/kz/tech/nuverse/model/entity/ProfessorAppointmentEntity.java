package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "professors_appointments", schema = "nuverse")
@Data
public class ProfessorAppointmentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "professor_id", insertable = false, updatable = false)
    private ProfessorEntity professor;

    @Column(name = "professor_id")
    private UUID professorId;

    @ManyToOne
    @JoinColumn(name = "appointment_id", insertable = false, updatable = false)
    private AppointmentEntity appointment;

    @Column(name = "appointment_id")
    private UUID appointmentId;
}
