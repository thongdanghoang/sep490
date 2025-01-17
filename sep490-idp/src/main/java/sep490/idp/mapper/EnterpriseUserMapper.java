package sep490.idp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.entity.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseUserMapper {

    @Mapping(target = "name", source = ".", qualifiedByName = "toFullName")
    EnterpriseUserDTO userEntityToEnterpriseUserDTO(UserEntity user);


    @Named("toFullName")
    default String translateToFullName(UserEntity user) {
        return user.getFirstName() + " " + user.getLastName();
    }
}
