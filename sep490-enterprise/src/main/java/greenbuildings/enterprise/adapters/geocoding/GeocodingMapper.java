package greenbuildings.enterprise.adapters.geocoding;

import greenbuildings.enterprise.dtos.AddressSuggestionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GeocodingMapper {
    
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "road", source = "address.road")
    @Mapping(target = "quarter", source = "address.quarter")
    @Mapping(target = "suburb", source = "address.suburb")
    @Mapping(target = "postcode", source = "address.postcode")
    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "countryCode", source = "address.countryCode")
    AddressSuggestionDTO toAddressSuggestion(GeocodingReverseResponse response);
}
