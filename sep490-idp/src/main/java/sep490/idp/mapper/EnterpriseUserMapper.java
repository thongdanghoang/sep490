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
    
    UserEntity newEnterpriseUserDTOToEntity(NewEnterpriseUserDTO dto);


    @Named("toFullName")
    default String translateToFullName(UserEntity user) {
        return new StringBuilder()
                .append(user.getFirstName())
                .append(" ")
                .append(user.getLastName())
                .toString();
    }
}
