package sep490.idp.dto;

import sep490.common.api.security.PermissionRole;
import sep490.common.api.security.UserScope;

import java.util.List;
import java.util.UUID;

public record NewEnterpriseUserDTO(String email, String firstName, String lastName, PermissionRole permissionRole, UserScope scope, List<UUID> buildings) {
}
