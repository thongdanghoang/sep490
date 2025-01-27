package sep490.idp.dto;

import sep490.common.api.security.PermissionRole;
import sep490.common.api.security.UserScope;
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
        String email,
        PermissionRole permissionRole,
        UserScope scope,
        LocalDateTime createdDate,
        List<UUID> buildings,
        Integer version
) {
    public static EnterpriseUserDetailDTO of(
            UserEntity userEntity
                                            ) {
        PermissionRole aggregatedRole = userEntity.getPermissions().stream()
                                                  .map(BuildingPermissionEntity::getRole)
                                                  .findFirst()
                                                  .orElse(null);
        
        return new EnterpriseUserDetailDTO(
                userEntity.getId(),
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                aggregatedRole,
                userEntity.getScope(),
                userEntity.getCreatedDate(),
                userEntity.getPermissions().stream().map(p -> p.getId().getBuildingId()).collect(Collectors.toList()),
                userEntity.getVersion()
        );
    }
}
