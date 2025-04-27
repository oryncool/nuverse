package kz.tech.nuverse.model.entity;

import jakarta.persistence.*;
import kz.tech.nuverse.model.dictionary.WeekDictionaryEntity;
import lombok.Data;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "appointments", schema = "nuverse")
@Data
public class AppointmentEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "week_id")
    private WeekDictionaryEntity week;

    private LocalTime startTime;
    private LocalTime endTime;
}
