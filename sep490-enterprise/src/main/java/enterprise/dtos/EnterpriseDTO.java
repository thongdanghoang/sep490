package enterprise.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import green_buildings.commons.api.BaseDTO;
import green_buildings.commons.api.utils.CommonConstant;

import java.util.UUID;

public record EnterpriseDTO(
        UUID id,
        int version,
        @NotBlank String name,
        @NotBlank @Pattern(regexp = CommonConstant.EMAIL_PATTERN) String email,
        @NotBlank @Pattern(regexp = CommonConstant.VIETNAM_PHONE_PATTERN) String hotline
) implements BaseDTO {
}
