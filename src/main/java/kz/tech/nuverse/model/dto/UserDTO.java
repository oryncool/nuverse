package kz.tech.nuverse.model.dto;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class UserDTO {
    private UUID id;
    private BaseDictionaryDTO role;
    private String name;
    private String surname;
    private String lastName;
    private LocalDate birthday;
    private String email;
    private String username;
}
