package greenbuildings.idp.security;

import commons.springfw.impl.securities.UserContextData;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import greenbuildings.commons.api.dto.auth.BuildingPermissionDTO;
import greenbuildings.idp.entity.UserEntity;

import java.util.List;

@Getter
public class MvcUserContextData extends UserContextData {
    private final transient UserEntity userEntity;
    
    public MvcUserContextData(@NotNull UserEntity userEntity,
                              List<GrantedAuthority> authorities,
                              List<BuildingPermissionDTO> permissions) {
        super(userEntity.getEmail(),
              userEntity.getEnterprise().getId(),
                userEntity.getPassword(),
              userEntity.getLocale(),
              List.copyOf(authorities),
              List.copyOf(permissions));
        this.userEntity = userEntity;
    }
}
