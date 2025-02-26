package greenbuildings.enterprise.mappers;

import greenbuildings.enterprise.dtos.BuildingDTO;
import greenbuildings.enterprise.entities.BuildingEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuildingMapper {
    BuildingEntity toEntity(BuildingDTO buildingDTO);
    
    BuildingDTO toDto(BuildingEntity buildingEntity);
    
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BuildingEntity partialUpdate(BuildingDTO buildingDTO, @MappingTarget
    BuildingEntity buildingEntity);
}