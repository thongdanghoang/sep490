package greenbuildings.enterprise.mappers;

import greenbuildings.enterprise.dtos.CreditConvertRatioDTO;
import greenbuildings.enterprise.entities.CreditConvertRatioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditConvertRatioMapper {
    
    CreditConvertRatioDTO toDTO(CreditConvertRatioEntity entity);
    
    CreditConvertRatioEntity toEntity(CreditConvertRatioDTO dto);

}
