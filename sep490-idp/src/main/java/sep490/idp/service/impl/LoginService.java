package sep490.idp.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.security.UserAuthenticationToken;
import sep490.idp.entity.UserEntity;
import sep490.idp.security.UserContextData;
import sep490.idp.utils.SecurityUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final BuildingPermissionRepository buildingPermissionRepository;

    public void login(UserEntity user) {
        List<BuildingPermissionEntity> permissions = buildingPermissionRepository.findAllByUserId(user.getId());
        var auth = new UserAuthenticationToken(new UserContextData(user, permissions));
        SecurityUtils.storeAuthenticationToContext(auth, request, response);
    }
}
