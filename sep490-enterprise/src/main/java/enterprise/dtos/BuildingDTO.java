package enterprise.dtos;

import green_buildings.commons.api.BaseDTO;

import java.util.UUID;

public record BuildingDTO(
        UUID id,
        int version
) implements BaseDTO {
}
