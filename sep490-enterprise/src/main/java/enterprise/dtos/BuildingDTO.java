package enterprise.dtos;

import green_buildings.commons.api.BaseDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record BuildingDTO(
        UUID id,
        int version,
        @NotBlank String name,
        @Min(1) int floors,
        @DecimalMin(value = "0.0", inclusive = true) BigDecimal squareMeters
) implements BaseDTO {
}
