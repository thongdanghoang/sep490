package green_buildings.idp.service.impl;

import green_buildings.commons.api.dto.auth.BuildingPermissionDTO;
import green_buildings.idp.entity.UserEntity;
import green_buildings.idp.repository.BuildingPermissionRepository;
import green_buildings.idp.security.MvcUserContextData;
import green_buildings.idp.security.PasskeyAuthenticationToken;
import green_buildings.idp.utils.IdpSecurityUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class LoginService {
    
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final BuildingPermissionRepository buildingPermissionRepository;
    
    public void login(UserEntity user) {
        var permissions = buildingPermissionRepository.findAllByUserId(user.getId());
        var buildingPermissions = permissions.stream()
                                             .map(e -> new BuildingPermissionDTO(e.getBuilding(), e.getRole()))
                                             .toList();
        var userContextData = new MvcUserContextData(user, IdpSecurityUtils.getAuthoritiesFromUserRole(user), buildingPermissions);
        var auth = new PasskeyAuthenticationToken(userContextData);
        IdpSecurityUtils.storeAuthenticationToContext(auth, request, response);
    }
}
