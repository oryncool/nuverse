package kz.tech.nuverse.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationRequest {
    private String name;
    private String surname;
    private String lastName;
    private String phone;
    private String email;
    private String password;
    private Long roleId;
    private String username;
    private LocalDate birthday;
}
