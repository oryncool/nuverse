package kz.tech.nuverse.model.dto.auth;

import kz.tech.nuverse.model.dto.dictionary.base.BaseDictionaryDTO;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class UserInfoDto {
    private UUID id;
    private String fullName;
    private String phone;
    private String email;
    private String additionalInfo;
    private BaseDictionaryDTO role;
    private OffsetDateTime createdDatetime;
    private OffsetDateTime lastModifiedDatetime;
    private boolean isActive;
}
