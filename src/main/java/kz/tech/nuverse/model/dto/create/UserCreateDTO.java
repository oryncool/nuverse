package kz.tech.nuverse.model.dto.create;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class UserCreateDTO {
    private UUID id;
    private BaseDictionaryDTO role;
    private String name;
    private String surname;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String username;
}
