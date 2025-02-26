package greenbuildings.enterprise.mappers;

import greenbuildings.enterprise.dtos.EnterpriseDTO;
import greenbuildings.enterprise.entities.EnterpriseEntity;
import greenbuildings.enterprise.mappers.decorators.EnterpriseMapperDecorator;
import greenbuildings.commons.api.events.PendingEnterpriseRegisterEvent;
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
