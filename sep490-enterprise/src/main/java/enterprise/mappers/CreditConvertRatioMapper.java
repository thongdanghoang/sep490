package enterprise.mappers;

import enterprise.dtos.CreditConvertRatioDTO;
import enterprise.entities.CreditConvertRatioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CreditConvertRatioMapper {
    
    CreditConvertRatioDTO toDTO(CreditConvertRatioEntity entity);
    
    CreditConvertRatioEntity toEntity(CreditConvertRatioDTO dto);

}
