package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.repository.UserRepository;
import sep490.idp.security.UserContextData;
import sep490.idp.utils.SecurityUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final BuildingPermissionRepository buildingPermissionRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        var permissions = buildingPermissionRepository.findAllByUserId(user.getId());
        return new UserContextData(user, SecurityUtils.getAuthoritiesFromUserRole(user), permissions);
    }
}
