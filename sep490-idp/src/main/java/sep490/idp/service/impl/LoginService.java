package sep490.idp.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.security.PasskeyAuthenticationToken;
import sep490.idp.security.UserContextData;
import sep490.idp.utils.SecurityUtils;

@Component
@RequiredArgsConstructor
@Transactional(rollbackFor = Throwable.class)
public class LoginService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final BuildingPermissionRepository buildingPermissionRepository;

    public void login(UserEntity user) {
        var permissions = buildingPermissionRepository.findAllByUserId(user.getId());
        var auth = new PasskeyAuthenticationToken(new UserContextData(user, SecurityUtils.getAuthoritiesFromUserRole(user), permissions));
        SecurityUtils.storeAuthenticationToContext(auth, request, response);
    }
}
