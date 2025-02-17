package enterprise.mappers;

import enterprise.dtos.CreditPackageDTO;
import enterprise.entities.CreditPackageEntity;
import enterprise.mappers.decorators.CreditPackageMapperDecorator;
import enterprise.mappers.decorators.EnterpriseMapperDecorator;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@DecoratedWith(CreditPackageMapperDecorator.class)
public interface CreditPackageMapper {
    
    CreditPackageDTO entityToDTO(CreditPackageEntity creditPackageEntity);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    CreditPackageEntity dtoToCreateCreditPackage(CreditPackageDTO dto);
    @Mapping(target = "id", ignore = true)
    CreditPackageEntity dtoToUpdateCreditPackage(@MappingTarget CreditPackageEntity entity, CreditPackageDTO dto);
}
