package sep490.idp.security;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sep490.common.api.security.UserRole;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;

import java.util.Collections;
import java.util.List;

@Getter
public class UserContextData implements UserDetails {
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private final transient UserEntity userEntity;
    private final transient List<BuildingPermissionEntity> permissions;
    
    public UserContextData(@NotNull UserEntity userEntity,
                           List<GrantedAuthority> authorities,
                           List<BuildingPermissionEntity> permissions) {
        this.username = userEntity.getEmail();
        this.password = userEntity.getPassword();
        this.authorities = authorities;
        this.userEntity = userEntity;
        this.permissions = permissions;
    }
}
