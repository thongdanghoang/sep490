package sep490.idp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import sep490.common.api.BaseDTO;
import sep490.common.api.dto.auth.BuildingPermissionDTO;
import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonConstant;

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
