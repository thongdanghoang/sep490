package green_buildings.idp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import green_buildings.commons.api.BaseDTO;
import green_buildings.commons.api.dto.auth.BuildingPermissionDTO;
import green_buildings.commons.api.security.UserRole;
import green_buildings.commons.api.security.UserScope;
import green_buildings.commons.api.utils.CommonConstant;

import java.util.List;
import java.util.UUID;

public record EnterpriseUserDetailsDTO(
        UUID id,
        int version,
        @NotBlank @Pattern(regexp = CommonConstant.EMAIL_PATTERN) String email,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull UserRole role,
        @NotNull UserScope scope,
        @Valid List<BuildingPermissionDTO> buildingPermissions
) implements BaseDTO {
}
