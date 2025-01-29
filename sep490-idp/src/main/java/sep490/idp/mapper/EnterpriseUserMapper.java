package sep490.idp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import sep490.idp.dto.BuildingPermissionDTO;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.EnterpriseUserDetailsDTO;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;

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
    UserEntity createNewEnterpriseUser(EnterpriseUserDetailsDTO dto);
    
    @Mapping(target = "buildingPermissions", source = "permissions")
    EnterpriseUserDetailsDTO userEntityToEnterpriseUserDetailDTO(UserEntity user);
    
    @Mapping(target = "transientHashCodeLeaked", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "emailVerified", ignore = true)
    @Mapping(target = "phone", ignore = true)
    @Mapping(target = "phoneVerified", ignore = true)
    void updateEnterpriseUser(@MappingTarget UserEntity user, EnterpriseUserDetailsDTO dto);
    
    @Mapping(target = "buildingId", source = "id.buildingId")
    BuildingPermissionDTO toBuildingPermissionDTO(BuildingPermissionEntity entity);
    
    @Named("toFullName")
    default String translateToFullName(UserEntity user) {
        return user.getFirstName() + " " + user.getLastName();
    }
    
}
