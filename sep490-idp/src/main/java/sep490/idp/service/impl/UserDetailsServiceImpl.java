package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sep490.common.api.dto.auth.BuildingPermissionDTO;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.repository.UserRepository;
import sep490.idp.security.MvcUserContextData;
import sep490.idp.utils.IdpSecurityUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final BuildingPermissionRepository buildingPermissionRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = userRepository.findByEmailWithBuildingPermissions(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        var permissions = buildingPermissionRepository.findAllByUserId(user.getId());
        var buildingPermissions = permissions.stream()
                                             .map(e -> new BuildingPermissionDTO(e.getBuilding(), e.getRole()))
                                             .toList();
        return new MvcUserContextData(user, IdpSecurityUtils.getAuthoritiesFromUserRole(user), buildingPermissions);
    }
}
