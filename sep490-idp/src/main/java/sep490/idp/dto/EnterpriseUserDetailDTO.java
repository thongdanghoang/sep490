package sep490.idp.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import sep490.common.api.security.PermissionRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonConstant;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
public record EnterpriseUserDetailDTO(
        UUID id,
        String firstName,
        String lastName,
        @Pattern(regexp = CommonConstant.EMAIL_PATTERN)
        String email,
        PermissionRole permissionRole,
        UserScope scope,
        LocalDateTime createdDate,
        List<UUID> buildings,
        @NotNull
        Integer version
) {
}
