package sep490.idp.security;


import commons.springfw.impl.securities.UserContextData;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import sep490.common.api.dto.auth.BuildingPermissionDTO;
import sep490.idp.entity.UserEntity;

import java.util.List;

@Getter
public class MvcUserContextData extends UserContextData {
    private final transient UserEntity userEntity;
    
    public MvcUserContextData(@NotNull UserEntity userEntity,
                              List<GrantedAuthority> authorities,
                              List<BuildingPermissionDTO> permissions) {
        super(userEntity.getEmail(), userEntity.getPassword(), List.copyOf(authorities), List.copyOf(permissions));
        this.userEntity = userEntity;
    }
}
