package enterprise.mappers;

import enterprise.dtos.EnterpriseDTO;
import enterprise.entities.EnterpriseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EnterpriseMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    EnterpriseEntity createEnterprise(EnterpriseDTO enterpriseDTO);
    
}
