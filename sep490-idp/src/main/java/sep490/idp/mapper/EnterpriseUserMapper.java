package sep490.idp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.NewEnterpriseUserDTO;
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
    UserEntity createNewEnterpriseUser(NewEnterpriseUserDTO dto);


    @Named("toFullName")
    default String translateToFullName(UserEntity user) {
        return new StringBuilder()
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .toString();
    }
}
