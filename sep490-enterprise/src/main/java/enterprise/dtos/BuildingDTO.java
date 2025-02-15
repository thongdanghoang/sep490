package enterprise.dtos;

import green_buildings.commons.api.BaseDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.util.UUID;

@Builder
public record BuildingDTO(
        UUID id,
        int version,
        @NotBlank String name,
        long numberOfDevices
) implements BaseDTO {
}
