package green_buildings.idp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import green_buildings.commons.api.security.UserRole;
import green_buildings.commons.api.security.UserScope;
import green_buildings.commons.api.utils.CommonConstant;

import java.util.UUID;

public record EnterpriseUserDTO(
        UUID id,
        @NotBlank String name,
        @NotBlank @Pattern(regexp = CommonConstant.EMAIL_PATTERN) String email,
        @NotNull UserRole role,
        @NotNull UserScope scope) {
}
