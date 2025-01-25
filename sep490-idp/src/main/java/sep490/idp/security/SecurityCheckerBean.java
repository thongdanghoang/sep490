package sep490.idp.security;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import sep490.idp.utils.SecurityUtils;

import java.util.UUID;

@Component
public class SecurityCheckerBean {
    public boolean checkIfUserHasPermission(UUID buildingId) {
        if (buildingId == null) {
            return false;
        }
        var permissions = SecurityUtils.getPermissions();
        if (CollectionUtils.isEmpty(permissions)) {
            return false;
        }
        return permissions.stream()
            .map(permission -> permission.getId().getBuildingId())
            .anyMatch(id -> id.equals(buildingId));
    }
}
