package greenbuildings.idp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import greenbuildings.commons.api.BaseDTO;
import greenbuildings.commons.api.dto.auth.BuildingPermissionDTO;
import greenbuildings.commons.api.security.UserRole;
import greenbuildings.commons.api.security.UserScope;
import greenbuildings.commons.api.utils.CommonConstant;

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
