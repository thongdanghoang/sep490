package commons.springfw.impl.securities;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import sep490.common.api.dto.auth.BuildingPermissionDTO;
import sep490.common.api.security.BuildingPermissionRole;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class JwtAuthenticationConverter
        implements Converter<Jwt, JwtAuthenticationTokenDecorator> {
    
    @Override
    public JwtAuthenticationTokenDecorator convert(Jwt source) {
        var email = Objects.requireNonNull(source.getClaims().get("sub")).toString();
        if (StringUtils.isBlank(email)) {
            return null;
        }
        
        var authoritiesClaim = Objects.requireNonNull(source.getClaims().get("authorities"));
        Set<SimpleGrantedAuthority> authorities = Collections.emptySet();
        if (authoritiesClaim instanceof List<?> authoritiesLists) {
            authorities = authoritiesLists
                    .stream()
                    .filter(String.class::isInstance)
                    .map(authority -> new SimpleGrantedAuthority((String) authority))
                    .collect(Collectors.toUnmodifiableSet());
        }
        
        var buildingPermissionsClaim = Objects.requireNonNull(source.getClaims().get("permissions"));
        List<BuildingPermissionDTO> buildingPermissions = Collections.emptyList();
        if (buildingPermissionsClaim instanceof List<?> buildingPermissionsList) {
            buildingPermissions = buildingPermissionsList
                    .stream()
                    .filter(LinkedTreeMap.class::isInstance)
                    .map(permission -> {
                        var permissionMap = (LinkedTreeMap) permission;
                        return new BuildingPermissionDTO(
                                UUID.fromString((String) permissionMap.get(BuildingPermissionDTO.Fields.buildingId)),
                                BuildingPermissionRole.valueOf((String) permissionMap.get(BuildingPermissionDTO.Fields.role))
                        );
                    })
                    .toList();
        }
        
        return new JwtAuthenticationTokenDecorator(
                source,
                new UserContextData(
                        email,
                        StringUtils.EMPTY,
                        List.copyOf(authorities),
                        List.copyOf(buildingPermissions)
                )
        );
    }
}
