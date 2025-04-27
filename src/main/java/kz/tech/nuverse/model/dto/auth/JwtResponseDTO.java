package kz.tech.nuverse.model.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponseDTO {

    private String accessToken;
    private String refreshToken;
}
