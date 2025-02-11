package green_buildings.idp.security;

import commons.springfw.impl.utils.SecurityUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import green_buildings.commons.api.dto.auth.BuildingPermissionDTO;

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
                .map(BuildingPermissionDTO::buildingId)
                .anyMatch(id -> id.equals(buildingId));
    }
}
