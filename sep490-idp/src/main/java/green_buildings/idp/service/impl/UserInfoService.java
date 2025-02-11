package green_buildings.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import green_buildings.commons.api.dto.auth.BuildingPermissionDTO;
import green_buildings.idp.entity.UserEnterpriseEntity;
import green_buildings.idp.entity.UserEntity;
import green_buildings.idp.repository.BuildingPermissionRepository;
import green_buildings.idp.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
        // TODO: [TRAB] check if user already login, why not get user entity from security context instead of use repository ?
        var user = userRepository.findByEmail(email).orElseThrow();
        var buildingPermissions = buildingPermissionRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(buildingPermission -> new BuildingPermissionDTO(
                        buildingPermission.getBuilding(),
                        buildingPermission.getRole()
                ))
                .toList();
        claims.put("enterpriseId", Optional.ofNullable(user.getEnterprise())
                                            .map(UserEnterpriseEntity::getEnterprise)
                                            .map(UUID::toString)
                                            .orElse(null));
        claims.put("permissions", buildingPermissions);
        return claims;
    }
}
