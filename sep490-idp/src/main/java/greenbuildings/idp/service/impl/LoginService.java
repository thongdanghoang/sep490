package greenbuildings.idp.service.impl;

import greenbuildings.commons.api.dto.auth.BuildingPermissionDTO;
import greenbuildings.idp.entity.UserEntity;
import greenbuildings.idp.repository.BuildingPermissionRepository;
import greenbuildings.idp.security.MvcUserContextData;
import greenbuildings.idp.security.PasskeyAuthenticationToken;
import greenbuildings.idp.utils.IdpSecurityUtils;
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
