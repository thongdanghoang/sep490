package sep490.idp.dto;

import jakarta.validation.constraints.NotNull;
import sep490.common.api.security.BuildingPermissionRole;

import java.util.UUID;

public record BuildingPermissionDTO(
        @NotNull UUID buildingId,
        @NotNull BuildingPermissionRole role
) {
}
