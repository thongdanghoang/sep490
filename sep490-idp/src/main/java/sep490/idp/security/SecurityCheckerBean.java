package sep490.idp.security;

import commons.springfw.impl.utils.SecurityUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import sep490.common.api.dto.auth.BuildingPermissionDTO;

import java.util.UUID;

@Component
public class SecurityCheckerBean {
    public boolean checkIfUserHasPermission(UUID buildingId) {
        if (buildingId == null) {
            return false;
        }
        var buildingPermissions = SecurityUtils.getPermissions();
        if (CollectionUtils.isEmpty(buildingPermissions)) {
            return false;
        }
        return buildingPermissions
                .stream()
                .map(BuildingPermissionDTO::id)
                .anyMatch(id -> id.equals(buildingId));
    }
}
