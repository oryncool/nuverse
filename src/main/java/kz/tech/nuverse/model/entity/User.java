package kz.tech.nuverse.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import kz.tech.nuverse.model.dictionary.RoleDictionaryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "users", schema = "nuverse")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleDictionaryEntity role;

    private String name;
    private String surname;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String phone;
    private String password;
    private String username;
}
