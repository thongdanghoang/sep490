package sep490.idp.dto;

import jakarta.validation.constraints.Pattern;
import sep490.common.api.BaseDTO;
import sep490.common.api.security.BuildingPermissionRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonConstant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record EnterpriseUserDetailDTO(
        UUID id,
        int version,
        String firstName,
        String lastName,
        @Pattern(regexp = CommonConstant.EMAIL_PATTERN)
        String email,
        BuildingPermissionRole permissionRole,
        UserScope scope,
        LocalDateTime createdDate,
        List<UUID> buildings
) implements BaseDTO {
}
