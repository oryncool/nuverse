package kz.tech.nuverse.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data

public class PhonebookDTO {
    private UUID id;
    private UserDTO user;
    private String phone;
}
