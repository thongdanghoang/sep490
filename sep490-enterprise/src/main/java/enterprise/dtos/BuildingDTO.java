package enterprise.dtos;

import sep490.common.api.BaseDTO;

import java.util.UUID;

public record BuildingDTO(
        UUID id,
        int version
) implements BaseDTO {
}
