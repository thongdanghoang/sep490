package enterprise.mappers;

import enterprise.dtos.EnterpriseDTO;
import enterprise.entities.EnterpriseEntity;
import enterprise.mappers.decorators.EnterpriseMapperDecorator;
import green_buildings.commons.api.events.PendingEnterpriseRegisterEvent;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(EnterpriseMapperDecorator.class)
public interface EnterpriseMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    EnterpriseEntity createEnterprise(EnterpriseDTO enterpriseDTO);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "wallet", ignore = true)
    EnterpriseEntity createEnterprise(PendingEnterpriseRegisterEvent event);
}
