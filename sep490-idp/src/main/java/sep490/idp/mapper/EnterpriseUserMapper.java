package sep490.idp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import sep490.common.api.security.BuildingPermissionRole;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.EnterpriseUserDetailDTO;
import sep490.idp.dto.NewEnterpriseUserDTO;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseUserMapper {
    
    @Mapping(target = "name", source = ".", qualifiedByName = "toFullName")
    EnterpriseUserDTO userEntityToEnterpriseUserDTO(UserEntity user);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "transientHashCodeLeaked", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    UserEntity createNewEnterpriseUser(NewEnterpriseUserDTO dto);
    
    @Mapping(target = "permissionRole", source = "permissions", qualifiedByName = "toPermissionRole")
    @Mapping(target = "buildings", source = "permissions", qualifiedByName = "toBuildingsId")
    EnterpriseUserDetailDTO userEntityToEnterpriseUserDetailDTO(UserEntity user);
    
    @Mapping(target = "transientHashCodeLeaked", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    void updateEnterpriseUser(@MappingTarget UserEntity user, EnterpriseUserDetailDTO dto);
    
    @Named("toFullName")
    default String translateToFullName(UserEntity user) {
        return user.getFirstName() + " " + user.getLastName();
    }
    
    @Named("toPermissionRole")
    default BuildingPermissionRole aggregatedRole(Set<BuildingPermissionEntity> permissions) {
        // TODO: [TRAB] check data: it seems frontend did not handle each buildingPermissionRole for each building
        return permissions
                .stream()
                .map(BuildingPermissionEntity::getRole)
                .findFirst()
                .orElse(null);
    }
    
    @Named("toBuildingsId")
    default List<UUID> mapperToBuildingsId(Set<BuildingPermissionEntity> permissions) {
        return permissions
                .stream()
                .map(p -> p.getId().getBuildingId())
                .toList();
    }
}
