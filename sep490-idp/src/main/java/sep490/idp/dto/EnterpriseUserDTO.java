package sep490.idp.dto;


import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;

public record EnterpriseUserDTO(
    String name, String email, UserRole role, UserScope scope
) { }
