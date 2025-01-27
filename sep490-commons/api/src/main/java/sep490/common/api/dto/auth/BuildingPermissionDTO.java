package sep490.common.api.dto.auth;

import lombok.experimental.FieldNameConstants;
import sep490.common.api.security.BuildingPermissionRole;

import java.util.UUID;

@FieldNameConstants
public record BuildingPermissionDTO(
        UUID id,
        BuildingPermissionRole role
) {
}
