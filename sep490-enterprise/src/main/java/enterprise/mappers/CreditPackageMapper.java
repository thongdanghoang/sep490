package enterprise.mappers;

import enterprise.dtos.CreditPackageDTO;
import enterprise.entities.CreditPackageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditPackageMapper {
    
    CreditPackageDTO entityToDTO(CreditPackageEntity creditPackageEntity);
    
}
