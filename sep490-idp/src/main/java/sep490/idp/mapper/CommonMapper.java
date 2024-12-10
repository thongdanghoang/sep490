package sep490.idp.mapper;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import sep490.common.api.dto.PageDTO;
import sep490.common.api.dto.SortDTO;
import sep490.common.api.enums.SortDirection;

import java.util.Objects;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonMapper {
    default Pageable toPageable(PageDTO searchPageDto, SortDTO sortDto) {
        var pageNumber = 0;
        var pageSize = 100;
        if (Objects.nonNull(searchPageDto)) {
            pageNumber = searchPageDto.pageNumber();
            pageSize = searchPageDto.pageSize();
        }
        if (Objects.nonNull(sortDto)
            && StringUtils.isNotBlank(sortDto.field())
            && Objects.nonNull(sortDto.direction())) {
            var direction = Objects.equals(sortDto.direction(), SortDirection.ASC)
                            ? Sort.Direction.ASC
                            : Sort.Direction.DESC;
            return PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortDto.field()));
        }
        return PageRequest.of(pageNumber, pageSize);
    }
}
