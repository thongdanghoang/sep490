package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import sep490.common.api.dto.auth.BuildingPermissionDTO;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    
    private final UserRepository userRepository;
    private final BuildingPermissionRepository buildingPermissionRepository;
    
    public OidcUserInfo loadUser(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        return new OidcUserInfo(buildUserClaims(user));
    }
    
    private Map<String, Object> buildUserClaims(UserEntity user) {
        return OidcUserInfo.builder()
                           .subject(user.getEmail())
                           .givenName(user.getFirstName())
                           .familyName(user.getLastName())
                           .email(user.getEmail())
                           .emailVerified(user.isEmailVerified())
                           .phoneNumber(user.getPhone())
                           .phoneNumberVerified(false)
                           .build()
                           .getClaims();
    }
    
    
    public Map<String, Object> getCustomClaimsForJwtAuthenticationToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        var user = userRepository.findByEmail(email).orElseThrow();
        var buildingPermissions = buildingPermissionRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(buildingPermission -> new BuildingPermissionDTO(
                        buildingPermission.getId().getBuildingId(),
                        buildingPermission.getRole()
                ))
                .toList();
        claims.put("permissions", buildingPermissions);
        return claims;
    }
}
