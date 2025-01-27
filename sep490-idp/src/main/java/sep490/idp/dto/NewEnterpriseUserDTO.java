package sep490.idp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import sep490.common.api.security.BuildingPermissionRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonConstant;

import java.util.List;
import java.util.UUID;

public record NewEnterpriseUserDTO(
        @NotBlank @Pattern(regexp = CommonConstant.EMAIL_PATTERN) String email,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull BuildingPermissionRole buildingPermissionRole,
        @NotNull UserScope scope,
        List<UUID> buildings) {
}
