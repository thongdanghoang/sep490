package sep490.common.api.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.FieldNameConstants;
import sep490.common.api.security.BuildingPermissionRole;

import java.util.UUID;

@FieldNameConstants
public record BuildingPermissionDTO(
        UUID buildingId,
        @NotNull BuildingPermissionRole role
) {
}
