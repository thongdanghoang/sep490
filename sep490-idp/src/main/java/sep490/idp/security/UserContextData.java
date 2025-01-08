package sep490.idp.security;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;

import java.util.Collections;
import java.util.List;

@Getter
public class UserContextData implements UserDetails {
    private final transient UserEntity userEntity;
    private final String username;
    private final String password;
    private final List<GrantedAuthority> authorities;
    private transient List<BuildingPermissionEntity> permissions;
    
    public UserContextData(@NotNull UserEntity userEntity) {
        this.userEntity = userEntity;
        this.username = userEntity.getEmail();
        this.password = userEntity.getPassword();
        // TODO: [Thong DANG HOANG] implement authorities
        this.authorities = Collections.emptyList();
    }

    public UserContextData(@NotNull UserEntity userEntity, List<BuildingPermissionEntity> permissions) {
        this(userEntity);
        this.permissions = permissions;
    }
}
