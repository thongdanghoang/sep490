package green_buildings.commons.api.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.experimental.FieldNameConstants;
import green_buildings.commons.api.security.BuildingPermissionRole;

import java.util.UUID;

@FieldNameConstants
public record BuildingPermissionDTO(
        UUID buildingId,
        @NotNull BuildingPermissionRole role
) {
}
