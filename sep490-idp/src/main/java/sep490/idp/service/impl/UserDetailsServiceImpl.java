package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.repository.UserRepository;
import sep490.idp.security.UserContextData;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final BuildingPermissionRepository buildingPermissionRepository;

    @Override
    public UserContextData loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + email);
        }
        List<BuildingPermissionEntity> permissions = buildingPermissionRepository.findAllByUserId(user.getId());
        return new UserContextData(user, permissions);
    }
}
