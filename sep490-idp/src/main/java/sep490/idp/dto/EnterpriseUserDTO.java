package sep490.idp.dto;


import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;

import java.util.UUID;

public record EnterpriseUserDTO(
        UUID id,
        String name,
        String email,
        UserRole role,
        UserScope scope) {
}
